package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.signets.JourRemplaceDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.JourRemplace
import ca.etsmtl.repository.data.model.signets.ListeJoursRemplaces
import ca.etsmtl.repository.data.model.signets.Session
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * Created by Sonphil on 24-06-18.
 */

class JourRemplaceRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: JourRemplaceDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns a list of days that replace others for a given session
     *
     * @param session The session
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @return A list of the user's sessions
     */
    fun getJoursRemplaces(session: Session, shouldFetch: Boolean = true): LiveData<Resource<List<JourRemplace>>> {
        return object : NetworkBoundResource<List<JourRemplace>, SignetsModel<ListeJoursRemplaces>>(appExecutors) {
            override fun saveCallResult(item: SignetsModel<ListeJoursRemplaces>) {
                item.data?.listeJours?.let { dao.insertAll(*it.toTypedArray()) }
            }

            override fun shouldFetch(data: List<JourRemplace>?): Boolean = shouldFetch

            override fun loadFromDb(): LiveData<List<JourRemplace>> = dao.getAll()

            override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeJoursRemplaces>>> {
                return transformApiLiveData(api.listeJoursRemplaces(session.abrege))
            }
        }.asLiveData()
    }
}