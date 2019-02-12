package ca.etsmtl.applets.etsmobile.presentation.schedule.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.repository.data.model.Seance
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator
import kotlinx.android.synthetic.main.fragment_schedule_week.*
import java.util.Calendar

/**
Created by mykaelll87 on 10/01/19
 */
class ScheduleWeekFragment : DaggerFragment(){

    private val adapter: ScheduleWeekAdapter = ScheduleWeekAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        return inflater.inflate(R.layout.fragment_schedule_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(LIST_TAG) }?.apply {
            val arrayList = this.getSerializable(LIST_TAG) as ArrayList<Seance>
            val seances = arrayList.toList()
            adapter.items = seances.groupBy {
                val day = Calendar.getInstance()
                day.clear()
                day.time = it.dateDebut
                day.set(Calendar.HOUR, 0)
                day.set(Calendar.MINUTE, 0)
                day.set(Calendar.SECOND, 0)
                day.set(Calendar.AM_PM,0)

                day
            }
            this.remove(LIST_TAG)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        recyclerViewSchedule.adapter = adapter
        recyclerViewSchedule.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewSchedule.itemAnimator = FadeInLeftAnimator()
    }

    companion object {
        const val TAG = "ScheduleWeekFragment"
        const val LIST_TAG = "ScheduleWeekFragmentListItems"
    }
}