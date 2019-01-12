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
import ca.etsmtl.applets.repository.util.timeInSeconds
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
    private val fetchSessionSeancesUseCase: FetchSessionSeancesUseCase,
    private val fetchSessionsUseCase: FetchSessionsUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
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

    // Seances
    private val seancesMediatorLiveData: MediatorLiveData<Resource<List<Seance>>> by lazy {
        MediatorLiveData<Resource<List<Seance>>>()
    }
    private var seancesLiveData: LiveData<Resource<List<Seance>>> = Transformations.switchMap(selectedSession) {
        it?.let {
            return@switchMap fetchSessionSeancesUseCase(it)
        }
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
    val seances: LiveData<Map<Date, List<Seance>>> = Transformations.map(seancesMediatorLiveData) {
        it.data.orEmpty().let {
            val thisWeekCal = Calendar.getInstance()
            val wantedWeek = thisWeekCal.get(Calendar.WEEK_OF_YEAR)
            val wantedYear = thisWeekCal.get(Calendar.YEAR)
            it.filter { s ->
                val dateCal = Calendar.getInstance()
                dateCal.time = s.dateDebut
                val dateWeek = dateCal.get(Calendar.WEEK_OF_YEAR)
                val dateYear = dateCal.get(Calendar.YEAR)

                wantedWeek == dateWeek && wantedYear == dateYear
            } // todo filter for the selected week
                .groupBy { s ->
                    val cal = Calendar.getInstance()
                    cal.clear()
                    cal.time = s.dateDebut
                    cal.set(Calendar.HOUR, 0)
                    cal.set(Calendar.MINUTE, 0)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.AM_PM, 0)
                    Date(cal.timeInMillis)
                }
        }
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

    private fun loadSessions() {
        sessionsLiveData = fetchSessionsUseCase().apply {
            sessionsMediatorLiveData.addSource(this) {
                sessionsMediatorLiveData.value = it
            }
            selectedSessionMediatorLiveData.addSource(this) {
                var currentSession = it.data.orEmpty()
                    .firstOrNull { s -> Date().timeInSeconds in s.dateDebut..s.dateFin }
                if (currentSession == null) {
                    currentSession = if (it.data?.isNotEmpty() == true) it.data?.get(0) else null
                }

                selectedSessionMediatorLiveData.value = currentSession
            }
        }
    }

    init {
        seancesMediatorLiveData.addSource(seancesLiveData) {
            seancesMediatorLiveData.value = it
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() {
        sessionsLiveData?.let {
            sessionsMediatorLiveData.removeSource(it)
            selectedSessionMediatorLiveData.removeSource(it)
        }
        sessionsLiveData = null

        loadSessions()
    }
}