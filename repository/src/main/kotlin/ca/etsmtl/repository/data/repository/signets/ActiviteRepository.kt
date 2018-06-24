package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.signets.ActiviteDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Activite
import ca.etsmtl.repository.data.model.signets.ListeDesActivitesEtProf
import ca.etsmtl.repository.data.model.signets.Session
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * Created by Sonphil on 23-06-18.
 */

class ActiviteRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: ActiviteDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns the student's activities (courses, labs, etc.) for the given session
     *
     * @param userCredentials The user's credentials
     * @param session The session
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getActivites(userCredentials: SignetsUserCredentials, session: Session, shouldFetch: Boolean = true): LiveData<Resource<List<Activite>>> {
        return object : NetworkBoundResource<List<Activite>, SignetsModel<ListeDesActivitesEtProf>>(appExecutors) {
            override fun saveCallResult(item: SignetsModel<ListeDesActivitesEtProf>) {
                item.data?.listeActivites?.let { dao.insertAll(*it.toTypedArray()) }
            }

            override fun shouldFetch(data: List<Activite>?): Boolean = shouldFetch

            override fun loadFromDb(): LiveData<List<Activite>> = dao.getAll()

            override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeDesActivitesEtProf>>> {
                return transformApiLiveData(api.listeDesActivitesEtProf(
                        userCredentials.codeAccesUniversel,
                        userCredentials.motPasse,
                        session.abrege
                ))
            }
        }.asLiveData()
    }
}