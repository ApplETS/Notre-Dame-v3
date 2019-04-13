package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeHoraireExamensFinauxRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toHoraireExamensFinauxEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.HoraireExamenFinalDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toHorairesExamensFinaux
import model.Resource
import model.HoraireExamenFinal
import model.Session
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * This repository provides access to the user's final exams.
 *
 * Created by Sonphil on 23-06-18.
 */

class HoraireExamenFinalRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: HoraireExamenFinalDao
) : SignetsRepository(appExecutors) {

    /**
     * Returns the schedules of the student's final exams with room locations
     *
     * @param userCredentials The user's credentials
     * @param session The session
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getHorairesExamensFinaux(
        userCredentials: SignetsUserCredentials,
        session: Session,
        shouldFetch: Boolean
    ): LiveData<Resource<List<HoraireExamenFinal>>> {
        return object : SignetsNetworkBoundResource<List<HoraireExamenFinal>, ApiListeHoraireExamensFinaux>(appExecutors) {
            override fun saveSignetsData(item: ApiListeHoraireExamensFinaux) {
                dao.clearAndInsertBySession(session.abrege, item.toHoraireExamensFinauxEntities(session))
            }

            override fun shouldFetch(data: List<HoraireExamenFinal>?) = shouldFetch

            override fun loadFromDb(): LiveData<List<HoraireExamenFinal>> {
                return Transformations.map(dao.getAllBySession(session.abrege)) {
                    it?.toHorairesExamensFinaux()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeHoraireExamensFinaux>>> {
                return api.listeHoraireExamensFinaux(
                        ListeHoraireExamensFinauxRequestBody(
                                userCredentials.codeAccesUniversel.value,
                                userCredentials.motPasse,
                                session.abrege
                        )
                )
            }
        }.asLiveData()
    }
}