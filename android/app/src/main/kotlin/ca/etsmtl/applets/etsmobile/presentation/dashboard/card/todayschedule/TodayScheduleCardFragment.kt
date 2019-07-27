package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.schedule.week.ScheduleWeekInnerListAdapter
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.shopify.livedataktx.observe
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_today_schedule_card.emptyViewToday
import kotlinx.android.synthetic.main.fragment_today_schedule_card.progressBarTodaySchedule
import kotlinx.android.synthetic.main.fragment_today_schedule_card.rvToday
import javax.inject.Inject

/**
 * Created by Sonphil on 24-01-19.
 */

class TodayScheduleCardFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val adapter: ScheduleWeekInnerListAdapter = ScheduleWeekInnerListAdapter()
    private val todayScheduleCardViewModel: TodayScheduleCardViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(TodayScheduleCardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_today_schedule_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        subscribeUI()
    }

    private fun setUpRecyclerView() {
        rvToday.adapter = adapter
        rvToday.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
    }

    private fun subscribeUI() {
        todayScheduleCardViewModel.seances.observe(this) {
            it?.takeIf { it.isNotEmpty() }?.let { seances ->
                adapter.items = seances
            }
        }

        todayScheduleCardViewModel.showEmptyView.observe(this) {
            it?.let {
                rvToday.isVisible = !it
                emptyViewToday.isVisible = it
            }
        }

        todayScheduleCardViewModel.errorMessage.observe(this, EventObserver {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        todayScheduleCardViewModel.loading.observe(this, Observer { visible ->
            progressBarTodaySchedule.isVisible = visible
        })
    }

    override fun onDestroyView() {
        rvToday.adapter = null

        super.onDestroyView()
    }

    companion object {
        val TAG = "TodayScheduleCardFragment"
        fun newInstance() = TodayScheduleCardFragment()
    }
}