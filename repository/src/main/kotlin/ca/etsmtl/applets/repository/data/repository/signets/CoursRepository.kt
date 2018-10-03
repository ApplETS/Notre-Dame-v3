package ca.etsmtl.applets.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toCoursEntity
import ca.etsmtl.applets.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toCours
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.NetworkBoundResource
import retrofit2.Response
import javax.inject.Inject

/**
 * This repository provides access to the courses.
 */
class CoursRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val coursDao: CoursDao,
    private val evaluationRepository: EvaluationRepository
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
        return object : NetworkBoundResource<List<Cours>, List<CoursEntity>>(appExecutors) {
            override fun saveCallResult(item: List<CoursEntity>) {
                coursDao.deleteAll()
                coursDao.insertAll(item)
            }

            override fun shouldFetch(data: List<Cours>?) = shouldFetch

            override fun loadFromDb(): LiveData<List<Cours>> {
                return Transformations.map(coursDao.getAll()) {
                    it?.toCours()
                }
            }

            override fun createCall(): LiveData<ApiResponse<List<CoursEntity>>> {
                return Transformations.switchMap(transformApiLiveData(api.listeCours(EtudiantRequestBody(userCredentials)))) {

                    val mediatorLiveData = MediatorLiveData<ApiResponse<List<CoursEntity>>>()

                    if (it.isSuccessful) {
                        val list: MutableList<CoursEntity> = mutableListOf()

                        with(it.body?.data?.liste) {
                            var nbCalls = 0

                            this?.forEach { apiCours ->
                                val coursEntity = apiCours.toCoursEntity("")

                                list.add(coursEntity)

                                if (apiCours.cote.isNullOrEmpty() && apiCours.hasValidSession()) {
                                    nbCalls++

                                    with(evaluationRepository.getEvaluationsSummary(
                                            userCredentials,
                                            coursEntity.toCours(),
                                            shouldFetch
                                    )) {
                                        mediatorLiveData.addSource(this) {
                                            if (it?.status == Resource.SUCCESS && it.data != null) {
                                                coursEntity.noteSur100 = it.data.noteSur100
                                            }

                                            if (it?.status != Resource.LOADING) {
                                                if (--nbCalls == 0) { // If this is the last call...
                                                    mediatorLiveData.value = ApiResponse(Response.success(list as List<CoursEntity>))
                                                }

                                                mediatorLiveData.removeSource(this)
                                            }
                                        }
                                    }
                                }
                            }

                            if (nbCalls == 0) { // If there is no call to be made...
                                mediatorLiveData.value = ApiResponse(Response.success(list as List<CoursEntity>))
                            }
                        }
                    } else {
                        mediatorLiveData.value = ApiResponse(Throwable(it.errorMessage))
                    }

                    mediatorLiveData
                }
            }
        }.asLiveData()
    }
}