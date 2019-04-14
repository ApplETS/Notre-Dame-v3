package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toCoursEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toCours
import model.Resource
import model.Cours
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * This repository provides access to the courses.
 */
class CoursRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val coursDao: CoursDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns the user's courses
     *
     * Furthermore, rated courses ([Cours.cote] != null) doesn't have a grade. The function will
     * fetch the grades for courses without a rate.
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @return The courses
     */
    fun getCours(
        userCredentials: SignetsUserCredentials,
        shouldFetch: Boolean = true
    ): LiveData<Resource<List<Cours>>> {
        return object : SignetsNetworkBoundResource<List<Cours>, ApiListeDeCours>(appExecutors) {
            override fun saveSignetsData(item: ApiListeDeCours) {
                coursDao.deleteAll()
                coursDao.insertAll(item.toCoursEntities())
            }

            override fun shouldFetch(data: List<Cours>?) = shouldFetch

            override fun loadFromDb(): LiveData<List<Cours>> {
                return Transformations.map(coursDao.getAllCoursEntityAndNoteSur100()) {
                    it.toCours()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeCours>>> {
                return api.listeCours(EtudiantRequestBody(userCredentials))
            }
        }.asLiveData()
    }
}