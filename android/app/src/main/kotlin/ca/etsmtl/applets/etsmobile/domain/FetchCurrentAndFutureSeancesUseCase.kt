package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ca.etsmtl.applets.repository.data.repository.signets.SessionRepository
import model.Resource
import model.Seance
import model.Session
import model.UserCredentials
import utils.date.ETSMobileDate
import javax.inject.Inject

/**
Created by mykaelll87 on 13/01/19
 */
class FetchCurrentAndFutureSeancesUseCase @Inject constructor(
    private val userCredentials: UserCredentials,
    private val sessionRepository: SessionRepository,
    private val fetchSessionSeancesUseCase: FetchSessionSeancesUseCase
) {
    private var numberOfSessionsToFetchSeancesFor: Int = 0

    operator fun invoke(): LiveData<Resource<List<Seance>>> {
        val result = MediatorLiveData<Resource<List<Seance>>>().apply {
            value = Resource.loading(null)
        }
        val sessionsSrc = sessionRepository.getSessions(
            userCredentials
        ) { true }

        result.addSource(sessionsSrc) { sessionRes ->
            if (sessionRes.status != Resource.Status.LOADING) {
                result.removeSource(sessionsSrc)

                sessionRes.data
                    ?.filter { it.isCurrentOrNext() }
                    .orEmpty()
                    .also { currentOrNextSessions ->
                        numberOfSessionsToFetchSeancesFor = currentOrNextSessions.count()

                        if (numberOfSessionsToFetchSeancesFor == 0) {
                            result.value = Resource.success(emptyList())
                        } else {
                            currentOrNextSessions.forEach { session ->
                                result.fetchSeances(session)
                            }
                        }
                    }
            }
        }

        return result
    }

    /**
     * Fetch the [Seance]s for the given [Session]
     *
     * The fetch is added as a source to the [MediatorLiveData]. When done, the source is removed.
     *
     * @param session [Session] to handle
     */
    private fun MediatorLiveData<Resource<List<Seance>>>.fetchSeances(session: Session) {
        val seancesSrc = fetchSessionSeancesUseCase(session)

        addSource(seancesSrc) { seancesRes ->
            // The seances returned to the caller
            val resultSeances = value?.data
                .orEmpty()
                .toMutableList()
                .apply {
                    // Add if not already present
                    seancesRes?.data.orEmpty().forEach {
                        if (!contains(it)) {
                            add(it)
                        }
                    }
                }

            if (seancesRes.status == Resource.Status.LOADING) {
                value = Resource.loading(resultSeances)
            } else { // Finished fetching the seances of the session
                numberOfSessionsToFetchSeancesFor--
                removeSource(seancesSrc)

                value = if (numberOfSessionsToFetchSeancesFor < 1) {
                    seancesRes.copyStatusAndMessage(resultSeances)
                } else {
                    Resource.loading(resultSeances)
                }
            }
        }
    }

    /**
     * Returns true if the [Session] is the current one or a next [Session]
     */
    private fun Session.isCurrentOrNext() = ETSMobileDate().run {
        dateFin >= this || dateDebut >= this
    }
}