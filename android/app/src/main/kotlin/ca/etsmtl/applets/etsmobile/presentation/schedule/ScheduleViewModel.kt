package ca.etsmtl.applets.etsmobile.presentation.schedule

import androidx.annotation.VisibleForTesting
import androidx.core.util.Pair
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchSeancesUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchSessionSeancesUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchSessionsUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import ca.etsmtl.applets.etsmobile.util.getGenericErrorMessage
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Seance
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.util.timeInSeconds
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
    private val fetchSeancesUseCase: FetchSeancesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private var seanceRes = object : RefreshableLiveData<Resource<List<Seance>>>(){
        override fun updateSource(): LiveData<Resource<List<Seance>>> {
            return fetchSeancesUseCase()
        }
    }

    val errorMessage: LiveData<Event<String?>> = seanceRes.nonNull().map {
        it.getGenericErrorMessage(app)
    }

    val seances: LiveData<Map<Pair<Calendar, Calendar>, List<Seance>>> = seanceRes.nonNull()
        .map { res->
            res.data?.groupBy {
                it.extractWeekPair()
            }
        }

    val loading: LiveData<Boolean> = seanceRes.map {
        it == null || it.status==Resource.Status.LOADING
    }

    val showEmptyView: LiveData<Boolean> = seanceRes.nonNull().map {
        it.status != Resource.Status.LOADING &&(it?.data ==null || it.data?.isEmpty()==true)
    }

    /**
     *  Finds the pair of calendar that represents the week that this seance belongs to
     */
    @VisibleForTesting
    private fun Seance.extractWeekPair(): Pair<Calendar, Calendar>{
        val beginningCal = Calendar.getInstance()
                    beginningCal.clear()
                    beginningCal.time = dateDebut
                    beginningCal.set(Calendar.DAY_OF_WEEK, 0)
                    beginningCal.set(Calendar.HOUR, 0)
                    beginningCal.set(Calendar.MINUTE, 0)
                    beginningCal.set(Calendar.SECOND, 0)
                    beginningCal.set(Calendar.AM_PM, 0)

        val endCal = beginningCal.clone() as Calendar
        endCal.add(Calendar.WEEK_OF_YEAR, 1)

        return Pair(beginningCal, endCal)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() = seanceRes.refresh()
}