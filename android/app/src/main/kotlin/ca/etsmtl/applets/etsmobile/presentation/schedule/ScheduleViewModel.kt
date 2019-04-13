package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.util.Range
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchFutureSeancesUseCase
import ca.etsmtl.applets.etsmobile.extension.getGenericErrorMessage
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import model.Resource
import model.Seance
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

    val errorMessage: LiveData<Event<String?>> = seanceRes.nonNull().map {
        if (app.getString(R.string.msg_api_no_seance) == it.message) {
            Event(null)
        } else
            it.getGenericErrorMessage(app)
    }

    val seances: LiveData<Map<Range<Calendar>, List<Seance>>> = seanceRes.nonNull()
        .map { res ->
            res.data?.groupBy {
                it.extractWeekRange()
            }
        }

    val loading: LiveData<Boolean> = seanceRes.map {
        it == null || it.status == Resource.Status.LOADING
    }

    val showEmptyView: LiveData<Boolean> = seanceRes.nonNull().map {
        it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true)
    }

    /**
     *  Finds the pair of calendar that represents the week that this seance belongs to
     */
    @VisibleForTesting
    private fun Seance.extractWeekRange(): Range<Calendar> {
        val beginningCal = Calendar.getInstance()

        beginningCal.timeInMillis = dateDebut.unixMillisLong
        beginningCal.set(Calendar.DAY_OF_WEEK, beginningCal.firstDayOfWeek)
        beginningCal.set(Calendar.HOUR_OF_DAY, 0)
        beginningCal.clear(Calendar.MINUTE)
        beginningCal.clear(Calendar.SECOND)

        val endCal = beginningCal.clone() as Calendar
        endCal.add(Calendar.WEEK_OF_YEAR, 1)

        return Range(beginningCal, endCal)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() = seanceRes.refresh()
}