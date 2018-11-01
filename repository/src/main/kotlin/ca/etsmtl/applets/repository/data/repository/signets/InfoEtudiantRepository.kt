package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toEtudiantEntity
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.EtudiantDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toEtudiant
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import javax.inject.Inject

/**
 * This repository provides access to the user's information.
 *
 * Created by Sonphil on 02-03-18.
 */

class InfoEtudiantRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: EtudiantDao
) : SignetsRepository(appExecutors) {

    /**
     * Returns the user's information
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch This function is called to determine whether the data should
     * be fetched from the network or only from the DB
     */
    fun getInfoEtudiant(
        userCredentials: SignetsUserCredentials,
        shouldFetch: (data: Etudiant?) -> Boolean
    ): LiveData<Resource<Etudiant>> {
        return object : SignetsNetworkBoundResource<Etudiant, ApiEtudiant>(appExecutors) {
            override fun saveSignetsData(item: ApiEtudiant) {
                dao.insert(item.toEtudiantEntity())
            }

            override fun shouldFetch(data: Etudiant?): Boolean = shouldFetch(data)

            override fun loadFromDb(): LiveData<Etudiant> {
                return Transformations.map(dao.getAll().transformToFirstItemLiveData()) {
                    it?.toEtudiant()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiEtudiant>>> {
                return api.infoEtudiant(EtudiantRequestBody(userCredentials))
            }
        }.asLiveData()
    }
}
