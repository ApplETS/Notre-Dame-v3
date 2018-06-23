package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.signets.SessionDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.ListeDeSessions
import ca.etsmtl.repository.data.model.signets.Session
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * This repository provide a list of the user's sessions.
 *
 * Created by Sonphil on 26-05-18.
 */

class SessionRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: SessionDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns a list of the user's sessions
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @return A list of the user's sessions
     */
    fun getSessions(
        userCredentials: SignetsUserCredentials,
        shouldFetch: Boolean
    ): LiveData<Resource<List<Session>>> = object : NetworkBoundResource<List<Session>, SignetsModel<ListeDeSessions>>(appExecutors) {
        override fun saveCallResult(item: SignetsModel<ListeDeSessions>) {
            item.data?.liste?.let { dao.insertAll(*it.toTypedArray()) }
        }

        override fun shouldFetch(data: List<Session>?): Boolean = shouldFetch

        override fun loadFromDb(): LiveData<List<Session>> = dao.getAll()

        override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeDeSessions>>> {
            return transformApiLiveData(api.listeSessions(userCredentials))
        }
    }.asLiveData()
}