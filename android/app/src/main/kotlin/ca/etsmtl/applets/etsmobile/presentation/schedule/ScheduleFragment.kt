package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_view_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.*
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
//    private var tabLayout: TabLayout? = null

    private val showTabsHandler = Handler()
    private var showTabsRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ScheduleAdapter(requireFragmentManager())
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefresh()
        setUpPager()
        btnRetry.setOnClickListener { scheduleViewModel.refresh() }
        subscribeUI()
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutSchedule.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutSchedule.setOnRefreshListener { scheduleViewModel.refresh() }
    }

    private fun setUpPager() {
        schedule_pager.adapter = adapter

        schedule_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                swipeRefreshLayoutSchedule.isEnabled = state == ViewPager.SCROLL_STATE_IDLE
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {}
        })

        (activity as? MainActivity)?.tabLayout?.let {
            showTabsRunnable = Runnable { it.isVisible = true }
            it.setupWithViewPager(schedule_pager)
            it.tabMode = TabLayout.MODE_SCROLLABLE
            showTabsHandler.postDelayed(
                showTabsRunnable,
                resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            )
        }
    }

//    private fun setUpRecyclerView() {
//        recyclerViewSchedule.adapter = adapter
//        recyclerViewSchedule.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//        recyclerViewSchedule.itemAnimator = FadeInLeftAnimator()
//    }

    private fun subscribeUI() {
        scheduleViewModel.seances.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    adapter.items = it
                    adapter.notifyDataSetChanged()
                    this.tabLayout?.getTabAt(adapter.getCurrentPosition())?.select()
                }
            }
        })

        scheduleViewModel.loading.observe(this, Observer {
            it?.let { swipeRefreshLayoutSchedule.isRefreshing = it }
        })

        scheduleViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        scheduleViewModel.showEmptyView.observe(this, Observer {
            it?.let {
                schedule_pager.isVisible = !it
                emptyViewSchedule.isVisible = it
            }
        })

        this.lifecycle.addObserver(scheduleViewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        showTabsHandler.removeCallbacks(showTabsRunnable)
        (activity as? MainActivity)?.tabLayout?.let {
            it.isVisible = false
            it.tabMode = TabLayout.MODE_FIXED
        }
    }

    companion object {
        private const val TAG = "ScheduleFragment"
        fun newInstance(): ScheduleFragment = ScheduleFragment()
    }
}
