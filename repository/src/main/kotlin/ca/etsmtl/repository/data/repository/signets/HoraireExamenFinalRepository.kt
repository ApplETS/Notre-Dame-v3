package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.api.response.mapper.toHoraireExamensFinauxEntities
import ca.etsmtl.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.repository.data.db.dao.signets.HoraireExamenFinalDao
import ca.etsmtl.repository.data.db.entity.mapper.toHorairesExamensFinaux
import ca.etsmtl.repository.data.model.HoraireExamenFinal
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.Session
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
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
        return object : NetworkBoundResource<List<HoraireExamenFinal>, ApiSignetsModel<ApiListeHoraireExamensFinaux>>(appExecutors) {
            override fun saveCallResult(item: ApiSignetsModel<ApiListeHoraireExamensFinaux>) {
                item.data?.let { dao.insertAll(*it.toHoraireExamensFinauxEntities(session).toTypedArray()) }
            }

            override fun shouldFetch(data: List<HoraireExamenFinal>?) = shouldFetch

            override fun loadFromDb(): LiveData<List<HoraireExamenFinal>> {
                return Transformations.map(dao.getAllBySession(session.abrege)) {
                    it.toHorairesExamensFinaux()
                }
            }

            override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeHoraireExamensFinaux>>> {
                return transformApiLiveData(api.listeHoraireExamensFinaux(
                        userCredentials.codeAccesUniversel,
                        userCredentials.motPasse,
                        session.abrege
                ))
            }
        }.asLiveData()
    }
}