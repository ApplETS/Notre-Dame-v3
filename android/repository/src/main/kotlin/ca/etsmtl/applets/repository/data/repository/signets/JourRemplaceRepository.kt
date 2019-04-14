package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.response.mapper.toJourRemplaceEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeJoursRemplaces
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.JourRemplaceDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toJoursRemplaces
import model.Resource
import model.JourRemplace
import model.Session
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
        return object : SignetsNetworkBoundResource<List<JourRemplace>, ApiListeJoursRemplaces>(appExecutors) {
            override fun saveSignetsData(item: ApiListeJoursRemplaces) {
                item.toJourRemplaceEntities(session)?.let { dao.clearAndInsertBySession(session.abrege, it) }
            }

            override fun shouldFetch(data: List<JourRemplace>?): Boolean = shouldFetch

            override fun loadFromDb(): LiveData<List<JourRemplace>> {
                return Transformations.map(dao.getAllBySession(session.abrege)) {
                    it?.toJoursRemplaces()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeJoursRemplaces>>> {
                return api.listeJoursRemplaces(session.abrege)
            }
        }.asLiveData()
    }
}