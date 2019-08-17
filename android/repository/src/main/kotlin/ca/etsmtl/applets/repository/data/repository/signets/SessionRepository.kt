package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toSessionEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.SessionDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toSessions
import model.Resource
import model.Session
import model.SignetsUserCredentials
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
        return object : SignetsNetworkBoundResource<List<Session>, ApiListeDeSessions>(appExecutors) {
            override fun saveSignetsData(item: ApiListeDeSessions) {
                dao.clearAndInsert(item.toSessionEntities())
            }

            override fun shouldFetch(data: List<Session>?) = shouldFetch(data)

            override fun loadFromDb(): LiveData<List<Session>> {
                return Transformations.map(dao.getAll()) { it.toSessions() }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeSessions>>> {
                return api.listeSessions(EtudiantRequestBody(userCredentials))
            }
        }.asLiveData()
    }
}