package ca.etsmtl.applets.etsmobile.presentation.schedule

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchSessionSeancesUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchSessionsUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.getGenericErrorMessage
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Seance
import ca.etsmtl.applets.repository.data.model.Session
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
    private val fetchSessionSeancesUseCase: FetchSessionSeancesUseCase,
    private val fetchSessionsUseCase: FetchSessionsUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {

    // Seances
    private val seancesMediatorLiveData: MediatorLiveData<Resource<List<Seance>>> by lazy {
        MediatorLiveData<Resource<List<Seance>>>()
    }
    private var seancesLiveData: LiveData<Resource<List<Seance>>>? = null
    // Sessions
    private val sessionsMediatorLiveData: MediatorLiveData<Resource<List<Session>>> by lazy {
        MediatorLiveData<Resource<List<Session>>>()
    }
    private var sessionsLiveData: LiveData<Resource<List<Session>>>? = null

    // TODO binder ça au option menu des sessions
    private val selectedSessionMediatorLiveData: MediatorLiveData<Session> by lazy {
        MediatorLiveData<Session>()
    }

    val selectedSession: LiveData<Session> = Transformations.map(selectedSessionMediatorLiveData) {
        it
    }

    val errorMessage: LiveData<Event<String?>> by lazy {
        val sessionMessage = Transformations.map(sessionsMediatorLiveData) {
            if (it.status == Resource.Status.ERROR) {
                it.getGenericErrorMessage(app)
            } else {
                Event(null)
            }
        }

        if (sessionMessage.value != null) {
            return@lazy sessionMessage
        } else {
            return@lazy Transformations.map(seancesMediatorLiveData) {
                if (app.getString(R.string.msg_api_no_seance) == it.message) {
                    Event(null)
                } else {
                    it.getGenericErrorMessage(app)
                }
            }
        }
    }
    val seances: LiveData<List<Seance>> = Transformations.map(seancesMediatorLiveData) {
        it.data
    }

    val sessions: LiveData<List<Session>> = Transformations.map(sessionsMediatorLiveData) {
        it.data
    }

    val loading: LiveData<Boolean> = Transformations.map(seancesMediatorLiveData) {
        it.status == Resource.Status.LOADING
    }
    // Todo Peut-être pas tant utile, le clearer si ça l'est pas
    val showNoSessionsView: LiveData<Boolean> = Transformations.map(sessionsMediatorLiveData) {
        it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true)
    }
    val showEmptyView: LiveData<Boolean> = Transformations.map(seancesMediatorLiveData) {
        it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true)
    }

    fun loadSeances() {
        seancesLiveData?.let { seancesMediatorLiveData.removeSource(it) }
        selectedSession.value?.let { s ->
            seancesLiveData = fetchSessionSeancesUseCase(s).apply {
                seancesMediatorLiveData.addSource(this) {
                    seancesMediatorLiveData.value = it
                }
            }
        }
    }

    private fun loadSessions() {
        sessionsLiveData = fetchSessionsUseCase().apply {
            sessionsMediatorLiveData.addSource(this) {
                sessionsMediatorLiveData.value = it
            }
            selectedSessionMediatorLiveData.addSource(this) {
                var currentSession = it.data.orEmpty()
                    .firstOrNull { s -> System.currentTimeMillis() in s.dateDebut..s.dateFin }
                if (currentSession == null) {
                    currentSession = if (it.data?.isNotEmpty() == true) it.data?.get(0) else null
                }

                selectedSessionMediatorLiveData.value = currentSession
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() {
        // Sessions
        sessionsLiveData?.let {
            sessionsMediatorLiveData.removeSource(it)
            selectedSessionMediatorLiveData.removeSource(it)
        }
        sessionsLiveData = null

        // Seances
        seancesLiveData?.let { seancesMediatorLiveData.removeSource(it) }
        seancesLiveData = null
        loadSessions()
    }
}