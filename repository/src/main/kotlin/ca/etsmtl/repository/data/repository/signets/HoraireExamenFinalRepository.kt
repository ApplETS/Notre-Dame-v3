package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.HoraireExamenFinalDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.HoraireExamenFinal
import ca.etsmtl.repository.data.model.signets.ListeHoraireExamensFinaux
import ca.etsmtl.repository.data.model.signets.Session
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
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
     * @param shouldFetch True if the should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getHorairesExamensFinaux(
        userCredentials: SignetsUserCredentials,
        session: Session,
        shouldFetch: Boolean
    ): LiveData<Resource<List<HoraireExamenFinal>>> {
        return object : NetworkBoundResource<List<HoraireExamenFinal>, SignetsModel<ListeHoraireExamensFinaux>>(appExecutors) {
            override fun saveCallResult(item: SignetsModel<ListeHoraireExamensFinaux>) {
                item.data?.listeHoraire?.let { dao.insertAll(*it.toTypedArray()) }
            }

            override fun shouldFetch(data: List<HoraireExamenFinal>?) = shouldFetch

            override fun loadFromDb(): LiveData<List<HoraireExamenFinal>> = dao.getAll()

            override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeHoraireExamensFinaux>>> {
                return transformApiLiveData(api.listeHoraireExamensFinaux(
                        userCredentials.codeAccesUniversel,
                        userCredentials.motPasse,
                        session.abrege
                ))
            }
        }.asLiveData()
    }
}