package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeDesSeancesRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toSeancesEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.SeanceDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toSeances
import model.Cours
import model.Resource
import model.Seance
import model.Session
import model.SignetsUserCredentials
import utils.date.plus
import utils.date.toDefaultSignetsDate
import javax.inject.Inject

/**
 * Created by Sonphil on 24-06-18.
 */

class SeanceRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: SeanceDao
) : SignetsRepository(appExecutors) {
    companion object {
        const val NB_MS_IN_A_DAY = 86400000L
    }

    /**
     * Returns the schedule of the sessions for a given course and a given session
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @param cours The course. If null, the [Seance]s will be fetched by [session].
     * @return The schedule of the sessions
     */
    fun getSeancesSession(
        userCredentials: SignetsUserCredentials,
        session: Session,
        cours: Cours? = null,
        shouldFetch: Boolean = true
    ): LiveData<Resource<List<Seance>>> {
        return object : SignetsNetworkBoundResource<List<Seance>, ApiListeDesSeances>(appExecutors) {
            override fun saveSignetsData(item: ApiListeDesSeances) {
                when (cours) {
                    null -> dao.clearAndInsertBySession(
                            session.abrege,
                            item.toSeancesEntities(session.abrege)
                    )
                    else -> dao.clearAndInsertByCoursAndSession(
                            cours.sigle,
                            session.abrege,
                            item.toSeancesEntities(session.abrege)
                    )
                }
            }

            override fun shouldFetch(data: List<Seance>?): Boolean = shouldFetch

            override fun loadFromDb(): LiveData<List<Seance>> {
                cours?.let {
                    return Transformations.map(dao.getByCoursAndSession(it.sigle, it.session)) {
                        it.toSeances()
                    }
                }

                return Transformations.map(dao.getBySession(session.abrege)) {
                    it.toSeances()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesSeances>>> {
                return api.listeDesSeances(
                        ListeDesSeancesRequestBody(
                                userCredentials.codeAccesUniversel.value,
                                userCredentials.motPasse,
                                cours?.sigle ?: "",
                                session.abrege,
                            session.dateDebut.toDefaultSignetsDate(),
                            (session.dateFin + NB_MS_IN_A_DAY).toDefaultSignetsDate()
                        )
                )
            }
        }.asLiveData()
    }
}