package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.api.response.mapper.toEtudiantEntity
import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.repository.data.db.dao.signets.EtudiantDao
import ca.etsmtl.repository.data.db.entity.mapper.toEtudiant
import ca.etsmtl.repository.data.model.Etudiant
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
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
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getInfoEtudiant(userCredentials: SignetsUserCredentials, shouldFetch: Boolean): LiveData<Resource<Etudiant>> {

        return object : NetworkBoundResource<Etudiant, ApiSignetsModel<ApiEtudiant>>(appExecutors) {
            override fun saveCallResult(item: ApiSignetsModel<ApiEtudiant>) {
                item.data?.let { dao.insert(it.toEtudiantEntity()) }
            }

            override fun shouldFetch(data: Etudiant?): Boolean = shouldFetch

            override fun loadFromDb(): LiveData<Etudiant> {
                return Transformations.map(getFirstItemLiveData(dao.getAll())) {
                    it?.toEtudiant()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiEtudiant>>> {
                return transformApiLiveData(api.infoEtudiant(userCredentials))
            }
        }.asLiveData()
    }
}
