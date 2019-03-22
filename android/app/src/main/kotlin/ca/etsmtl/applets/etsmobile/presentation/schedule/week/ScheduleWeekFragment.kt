package ca.etsmtl.applets.etsmobile.presentation.schedule.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import model.Seance
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator
import kotlinx.android.synthetic.main.fragment_schedule_week.*

/**
Created by mykaelll87 on 10/01/19
 */
class ScheduleWeekFragment : DaggerFragment() {

    private val adapter: ScheduleWeekAdapter = ScheduleWeekAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_schedule_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(LIST_TAG) }?.apply {
            val arrayList = this.getSerializable(LIST_TAG) as ArrayList<Seance>
            adapter.items = arrayList.toList()
            this.remove(LIST_TAG)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerViewSchedule.adapter = adapter
        recyclerViewSchedule.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewSchedule.itemAnimator = FadeInLeftAnimator()
    }

    companion object {
        const val TAG = "ScheduleWeekFragment"
        const val LIST_TAG = "ScheduleWeekFragmentListItems"
    }
}