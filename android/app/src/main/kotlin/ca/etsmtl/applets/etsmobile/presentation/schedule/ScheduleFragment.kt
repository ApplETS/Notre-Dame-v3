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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.EventObserver
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator
import kotlinx.android.synthetic.main.empty_view_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.*
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
                adapter.items = seances
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

        this.lifecycle.addObserver(scheduleViewModel)
    }

    companion object {
        private const val TAG = "ScheduleFragment"
        fun newInstance(): ScheduleFragment = ScheduleFragment()
    }
}
