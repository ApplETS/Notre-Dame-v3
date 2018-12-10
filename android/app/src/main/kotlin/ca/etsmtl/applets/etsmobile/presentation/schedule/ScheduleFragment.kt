package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.util.isVisible
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator
import kotlinx.android.synthetic.main.empty_view_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * Created by Sonphil on 25-02-18.
 */
class ScheduleFragment : DaggerFragment() {
    private val scheduleViewModel: ScheduleViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ScheduleViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter: ScheduleAdapter = ScheduleAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefresh()
        setUpRecyclerView()
        subscribeUI()
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutSchedule.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutSchedule.setOnRefreshListener { scheduleViewModel.refresh() }
    }

    private fun setUpRecyclerView() {
        recyclerViewSchedule.adapter = adapter
        recyclerViewSchedule.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewSchedule.itemAnimator = FadeInLeftAnimator()
    }

    private fun subscribeUI() {
        scheduleViewModel.sessions.observe(this, Observer {
            // TODO utiliser les sessions dans un option menu
        })
        scheduleViewModel.seances.observe(this, Observer {
            it?.takeIf { it.isNotEmpty() }?.let { seances ->
                val thisWeekCal = Calendar.getInstance()
                val wantedWeek = thisWeekCal.get(Calendar.WEEK_OF_YEAR)
                val wantedYear = thisWeekCal.get(Calendar.YEAR)
                adapter.items = seances
                    .filter { s ->
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
        })

        scheduleViewModel.showEmptyView.observe(this, Observer {
            recyclerViewSchedule.isVisible = !it
            emptyViewSchedule.isVisible = it
        })
        scheduleViewModel.loading.observe(this, Observer {
            it?.let { swipeRefreshLayoutSchedule.isRefreshing = it }
        })

        scheduleViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        scheduleViewModel.selectedSession.observe(this, Observer {
            scheduleViewModel.loadSeances()
        })

        this.lifecycle.addObserver(scheduleViewModel)
    }

    companion object {
        private const val TAG = "ScheduleFragment"
        fun newInstance(): ScheduleFragment = ScheduleFragment()
    }
}
