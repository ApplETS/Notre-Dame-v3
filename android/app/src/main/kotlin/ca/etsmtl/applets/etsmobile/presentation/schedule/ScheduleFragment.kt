package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.alamkanak.weekview.MonthChangeListener
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.empty_view_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import model.Seance
import utils.date.toCalendar
import java.util.*
import javax.inject.Inject

/**
 * Created by Mykaelll87 on 15-01-19.
 */
class ScheduleFragment : DaggerFragment() {
    private val scheduleViewModel: ScheduleViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ScheduleViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var adapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ScheduleAdapter(childFragmentManager)
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpWeekView()
        btnRetry.setOnClickListener { scheduleViewModel.refresh() }
        subscribeUI()
    }

    private fun setUpWeekView() {
        weekView.setShowTimeColumnHourSeparator(true)
        val monthChangeListener = object : MonthChangeListener<Seance> {
            override fun onMonthChange(startDate: Calendar, endDate: Calendar): List<WeekViewDisplayable<Seance>> {
                return scheduleViewModel.getSeancesForDates(startDate, endDate).map { seance ->
                    seance.toWeekViewEvent() as WeekViewDisplayable<Seance>
                }
            }
        }
        weekView.goToCurrentTime()
        (weekView as WeekView<Seance>).setMonthChangeListener(monthChangeListener)
    }

    private fun Seance.toWeekViewEvent(): WeekViewEvent<Seance> {
        val style = WeekViewEvent.Style
            .Builder()
            .setBackgroundColor(sigleCours.run {
                this.hashCode()
            })
            .build()

        return WeekViewEvent.Builder<Seance>()
            .setTitle(libelleCours + "\n" + sigleCours)
            .setLocation("\n" + local)
            .setStartTime(dateDebut.toCalendar())
            .setEndTime(dateFin.toCalendar())
            .setStyle(style)
            .build()
    }

    private fun subscribeUI() {
        scheduleViewModel.seances.observe(this, Observer {
            weekView.notifyDataSetChanged()
        })

        scheduleViewModel.loading.observe(this, Observer {
            it?.let { progressBarSchedule.isVisible = it }
        })

        scheduleViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        scheduleViewModel.showEmptyView.observe(this, Observer {
            it?.let {
                emptyViewSchedule.isVisible = it
            }
        })

        this.lifecycle.addObserver(scheduleViewModel)
    }

    companion object {
        private const val TAG = "ScheduleFragment"
        fun newInstance(): ScheduleFragment = ScheduleFragment()
    }
}
