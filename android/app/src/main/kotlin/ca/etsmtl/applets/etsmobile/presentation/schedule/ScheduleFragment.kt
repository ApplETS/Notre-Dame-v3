package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.util.ProgressTimeLatch
import com.alamkanak.weekview.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.empty_view_schedule.btnRetry
import kotlinx.android.synthetic.main.empty_view_schedule.emptyViewSchedule
import kotlinx.android.synthetic.main.fragment_schedule.progressBarSchedule
import kotlinx.android.synthetic.main.fragment_schedule.weekView
import kotlinx.android.synthetic.main.fragment_schedule.btnToday
import model.Seance
import utils.date.isToday
import utils.date.toCalendar
import utils.date.toETSMobileDate
import java.util.Calendar
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
    private val progressTimeLatch = ProgressTimeLatch(300, 900) {
        progressBarSchedule?.isVisible = it
    }

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

        setupWeekView()
        btnRetry.setOnClickListener { scheduleViewModel.refresh() }
        btnToday.setOnClickListener { weekView.goToToday() }
        subscribeUI()
    }

    private fun setupWeekView() {
        weekView.setShowTimeColumnHourSeparator(true)

        // Listener called when the week view needs to load another month
        val monthChangeListener = object : MonthChangeListener<Seance> {
            override fun onMonthChange(startDate: Calendar, endDate: Calendar): List<WeekViewDisplayable<Seance>> {
                return scheduleViewModel.getSeancesForDates(startDate, endDate).map { seance ->
                    seance.toWeekViewEvent() as WeekViewDisplayable<Seance>
                }
            }
        }
        (weekView as WeekView<Seance>).setMonthChangeListener(monthChangeListener)

        weekView.scrollListener = object : ScrollListener {
            override fun onFirstVisibleDayChanged(
                newFirstVisibleDay: Calendar,
                oldFirstVisibleDay: Calendar?
            ) {
                val newFirstVisibleDayIsToday = newFirstVisibleDay
                    .toETSMobileDate(newFirstVisibleDay.timeInMillis)
                    .isToday()

                if (newFirstVisibleDayIsToday) {
                    btnToday.hide()
                } else {
                    btnToday.show()
                }
            }
        }
    }

    private fun Seance.toWeekViewEvent(): WeekViewEvent<Seance> {
        val style = WeekViewEvent.Style
            .Builder()
            .setBackgroundColor(ColorUtils.blendARGB(
                sigleCours.hashCode(),
                Color.BLACK,
                0.3f
            ))
            .build()

        return WeekViewEvent.Builder<Seance>()
            .setTitle(libelleCours)
            .setLocation("\n$sigleCours $nomActivite\n$local")
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
            it?.let { progressTimeLatch.refreshing = it }
        })

        scheduleViewModel.errorMessage.observe(this, EventObserver {
            it?.let {
                if(!it.equals(context?.getString(R.string.error_no_internet_connection)))
                Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        scheduleViewModel.showEmptyView.observe(this, Observer {
            it?.let {
                emptyViewSchedule.isVisible = it
                weekView.isVisible = !it
            }
        })

        this.lifecycle.addObserver(scheduleViewModel)
    }
}
