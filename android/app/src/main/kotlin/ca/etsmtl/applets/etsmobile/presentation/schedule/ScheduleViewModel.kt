package ca.etsmtl.applets.etsmobile.presentation.schedule

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchCurrentAndFutureSeancesUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchSchedulePref
import ca.etsmtl.applets.etsmobile.extension.getGenericErrorMessage
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import com.shopify.livedataktx.map
import model.Resource
import model.Seance
import utils.date.ETSMobileDate
import utils.date.isToday
import utils.date.toCalendar
import utils.date.toETSMobileDate
import java.util.Calendar
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
    private val fetchCurrentAndFutureSeancesUseCase: FetchCurrentAndFutureSeancesUseCase,
    private val fetchSchedulePref: FetchSchedulePref,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private var seanceRes = object : RefreshableLiveData<Resource<List<Seance>>>() {
        override fun updateSource(): LiveData<Resource<List<Seance>>> {
            return fetchCurrentAndFutureSeancesUseCase()
        }
    }

    val errorMessage: LiveData<Event<String?>> = Transformations.map(seanceRes) {
        if (app.getString(R.string.msg_api_no_seance) == it.message) {
            Event(null)
        } else if (seanceRes.value?.status == Resource.Status.SUCCESS && seanceRes.value?.data.isNullOrEmpty()) {
            Event(app.getString(R.string.error_no_event_found))
        } else {
            it.getGenericErrorMessage(app)
        }
    }

    val seances: LiveData<List<Seance>> = Transformations.map(seanceRes) { res ->
        res.data
    }

    val loading: LiveData<Boolean> = seanceRes.map {
        it?.status == Resource.Status.LOADING
    }

    private val showTodayButtonPref = MutableLiveData<Boolean>()
    private val currentDay = MutableLiveData<ETSMobileDate>()
    val showTodayButton = MutableLiveData<Boolean>()
    val numberOfVisibleDays = MutableLiveData<Int>()
    val xScrollingSpeed = MutableLiveData<Float>()
    val scrollDuration = MutableLiveData<Int>()

    fun getSeancesForDates(startDate: Calendar, endDate: Calendar): List<Seance> {
        val seances = seanceRes.value?.data

        return seances?.filter { seance ->
            seance.dateDebut.toCalendar() in startDate..endDate
        }.orEmpty()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init() {
        seanceRes.refreshIfValueIsNull()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun loadPreferences() {
        showTodayButtonPref.value = fetchSchedulePref.showTodayButton()
        showTodayButton.value = showTodayButtonPref.value
        numberOfVisibleDays.value = fetchSchedulePref.numberOfVisibleDays()
        xScrollingSpeed.value = fetchSchedulePref.xScrollingSpeed()
        scrollDuration.value = fetchSchedulePref.scrollDuration()
    }

    fun refresh() = seanceRes.refresh()

    /**
     * Should be called when the day is changed
     *
     * @param newFirstVisibleDay
     */
    fun onDayChanged(newFirstVisibleDay: Calendar) {
        if (showTodayButtonPref.value == true) {
            currentDay.value = newFirstVisibleDay.toETSMobileDate(newFirstVisibleDay.timeInMillis)

            // Hide today button if it's today
            showTodayButton.value = currentDay.value?.isToday()?.not() ?: true
        }
    }
}