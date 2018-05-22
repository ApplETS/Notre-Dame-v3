package ca.etsmtl.etsmobile.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile.AppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.ProgrammeDao
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.ListeProgrammes
import ca.etsmtl.etsmobile.data.model.signets.Programme
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * This repository provides access to the user's programs.
 *
 * Created by Sonphil on 17-05-18.
 */

class ProgrammeRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: ProgrammeDao
) : SignetsRepository(appExecutors) {

    /**
     * Returns the user's program
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch Whether the programs should be fetched from the network or only from the
     * DB
     */
    fun getProgrammes(userCredentials: SignetsUserCredentials, shouldFetch: Boolean = true): LiveData<Resource<List<Programme>>> {

        return object : NetworkBoundResource<List<Programme>, SignetsModel<ListeProgrammes>>(appExecutors) {
            override fun saveCallResult(item: SignetsModel<ListeProgrammes>) {
                item.data?.liste?.let { dao.insertAll(*it.toTypedArray()) }
            }

            override fun shouldFetch(data: List<Programme>?): Boolean {
                return shouldFetch
            }

            override fun loadFromDb(): LiveData<List<Programme>> {
                return dao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeProgrammes>>> {
                return transformsApiLiveData(api.listeProgrammes(userCredentials))
            }
        }.asLiveData()
    }
}