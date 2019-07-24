package ca.etsmtl.applets.etsmobile.presentation.schedule

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchFutureSeancesUseCase
import ca.etsmtl.applets.etsmobile.extension.getGenericErrorMessage
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import com.shopify.livedataktx.map
import model.Resource
import model.Seance
import utils.date.toCalendar
import java.util.Calendar
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
    private val fetchFutureSeancesUseCase: FetchFutureSeancesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private var seanceRes = object : RefreshableLiveData<Resource<List<Seance>>>() {
        override fun updateSource(): LiveData<Resource<List<Seance>>> {
            return fetchFutureSeancesUseCase()
        }
    }

    val errorMessage: LiveData<Event<String?>> = Transformations.map(seanceRes) {
        if (app.getString(R.string.msg_api_no_seance) == it.message) {
            Event(null)
        } else
            it.getGenericErrorMessage(app)
    }

    val seances: LiveData<List<Seance>> = Transformations.map(seanceRes) { res ->
        res.data
    }

    val loading: LiveData<Boolean> = seanceRes.map {
        it == null || it.status == Resource.Status.LOADING
    }

    val showEmptyView: LiveData<Boolean> = Transformations.map(seanceRes) {
        it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true)
    }

    fun getSeancesForDates(startDate: Calendar, endDate: Calendar): List<Seance> {
        val seances = seanceRes.value?.data

        if (seances != null) {
            return seances.filter { seance ->
                seance.dateDebut.toCalendar() in startDate..endDate
            }
        } else {
            return emptyList()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init() = seanceRes.refreshIfValueIsNull()

    fun refresh() = seanceRes.refresh()
}