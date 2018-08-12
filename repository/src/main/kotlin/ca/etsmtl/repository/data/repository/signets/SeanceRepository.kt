package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.api.response.mapper.toSeancesEntities
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.repository.data.db.dao.signets.SeanceDao
import ca.etsmtl.repository.data.db.entity.mapper.toSeances
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.Seance
import ca.etsmtl.repository.data.model.Session
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * Created by Sonphil on 24-06-18.
 */

class SeanceRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: SeanceDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns the schedule of the sessions for a given course and a given session
     *
     * @param userCredentials The user's credentials
     * @param cours The course
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @return The schedule of the sessions
     */
    fun getSeancesSession(
        userCredentials: SignetsUserCredentials,
        cours: Cours,
        session: Session,
        shouldFetch: Boolean = true
    ): LiveData<Resource<List<Seance>>> {
        return object : NetworkBoundResource<List<Seance>, ApiSignetsModel<ApiListeDesSeances>>(appExecutors) {
            override fun saveCallResult(item: ApiSignetsModel<ApiListeDesSeances>) {
                item.data?.let { dao.clearAndInsertByCoursAndSession(cours.sigle, cours.session, it.toSeancesEntities(cours)) }
            }

            override fun shouldFetch(data: List<Seance>?): Boolean = shouldFetch

            override fun loadFromDb(): LiveData<List<Seance>> {
                return Transformations.map(dao.getByCoursAndSession(cours.sigle, cours.session)) {
                    it.toSeances()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesSeances>>> {
                return transformApiLiveData(api.listeDesSeances(
                        userCredentials.codeAccesUniversel,
                        userCredentials.motPasse,
                        cours.sigle,
                        session.abrege,
                        session.dateDebut,
                        session.dateFin
                ))
            }
        }.asLiveData()
    }
}