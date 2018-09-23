package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.repository.data.api.response.mapper.toSessionEntities
import ca.etsmtl.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.repository.data.db.dao.signets.SessionDao
import ca.etsmtl.repository.data.db.entity.mapper.toSessions
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.Session
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

class SessionRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: SessionDao
) : SignetsRepository(appExecutors) {
    
    /**
     * Returns the user's sessions
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch This function is called to determine whether the data should be fetched
     * from the network or only from the DB
     */
    fun getSessions(
        userCredentials: SignetsUserCredentials,
        shouldFetch: (data: List<Session>?) -> Boolean
    ): LiveData<Resource<List<Session>>> {
        return object : NetworkBoundResource<List<Session>, ApiSignetsModel<ApiListeDeSessions>>(appExecutors) {
            override fun saveCallResult(item: ApiSignetsModel<ApiListeDeSessions>) {
                dao.deleteAll()
                item.data?.toSessionEntities()?.let { dao.insertAll(it) }
            }

            override fun shouldFetch(data: List<Session>?) = shouldFetch(data)

            override fun loadFromDb(): LiveData<List<Session>> {
                return Transformations.map(dao.getAll()) { it.toSessions() }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeSessions>>> {
                return transformApiLiveData(api.listeSessions(EtudiantRequestBody(userCredentials)))
            }
        }.asLiveData()
    }
}