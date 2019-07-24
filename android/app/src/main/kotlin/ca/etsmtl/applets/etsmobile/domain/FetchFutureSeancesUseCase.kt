package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.repository.signets.SessionRepository
import model.Resource
import model.Seance
import model.Session
import model.SignetsUserCredentials
import utils.date.ETSMobileDate
import javax.inject.Inject

/**
Created by mykaelll87 on 13/01/19
 */
class FetchFutureSeancesUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val sessionRepository: SessionRepository,
    private val fetchSessionSeancesUseCase: FetchSessionSeancesUseCase,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<List<Seance>>> {
        val now = ETSMobileDate()
        val seanceFetchStatus = mutableMapOf<Session, Boolean>()
        var sessionFetchDone = false
        val mediatorLiveData = MediatorLiveData<Resource<List<Seance>>>()

        mediatorLiveData.value = Resource.loading(emptyList())

        return Transformations.switchMap(sessionRepository.getSessions(userCredentials) { true }) { resSessions ->
            val sessions = resSessions.data.orEmpty()
            var latestError: String? = null

            fun updateMediatorLiveData(res: Resource<List<Seance>>) {

                val seances = mutableListOf<Seance>()
                mediatorLiveData.value?.data?.let {
                    seances.addAll(it)
                }
                seances.addAll(res.data.orEmpty().filter { 
                    !seances.contains(it) && it.dateDebut >= now
                })

                val error = latestError

                if (!sessionFetchDone || seanceFetchStatus.any { !it.value }) {
                    mediatorLiveData.value = Resource.loading(seances)
                } else if (!error.isNullOrBlank()) {
                    mediatorLiveData.value = Resource.error(error, seances)
                } else {
                    mediatorLiveData.value = Resource.success(seances)
                }
            }

            fun fetchSeancesFromSession(session: Session) {
                seanceFetchStatus[session] = false
                mediatorLiveData.addSource(
                    fetchSessionSeancesUseCase(session)
                ) { res ->
                    if (res.status == Resource.Status.ERROR) {
                        seanceFetchStatus[session] = true
                        latestError = res.message
                    }
                    if (res.status == Resource.Status.SUCCESS) {
                        seanceFetchStatus[session] = true
                    }

                    updateMediatorLiveData(res)
                }
            }

            sessions.forEach {
                if (it.dateFin >= now && !seanceFetchStatus.contains(it)) {
                    fetchSeancesFromSession(it)
                }
            }

            if (resSessions.status === Resource.Status.ERROR) {
                mediatorLiveData.value = Resource.error(resSessions.message ?: app.getString(R.string.error), emptyList())
            }
            if (resSessions.status === Resource.Status.SUCCESS) {
                sessionFetchDone = true
            }

            mediatorLiveData
        }
    }
}