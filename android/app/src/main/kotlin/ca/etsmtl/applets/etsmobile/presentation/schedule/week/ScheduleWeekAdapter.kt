package ca.etsmtl.applets.etsmobile.presentation.schedule.week

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import model.Seance
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*
import java.util.Calendar

/**
 * Created by mykaelll87 on 2019-01-15
 */
class ScheduleWeekAdapter : RecyclerView.Adapter<ScheduleWeekAdapter.SeanceDayViewHolder>() {

    private var itemList: List<Map.Entry<Calendar, List<Seance>>> = emptyList()
    var items: List<Seance> = emptyList()
        set(value) {
            field = value
            val newItemsList = mutableListOf<Map.Entry<Calendar, List<Seance>>>().apply {
                value.groupBy {
                    val day = Calendar.getInstance()
                    day.clear()
                    day.timeInMillis = it.dateDebut.unixMillisLong
                    day.set(Calendar.HOUR, 0)
                    day.set(Calendar.MINUTE, 0)
                    day.set(Calendar.SECOND, 0)
                    day.set(Calendar.AM_PM, 0)

                    day
                }.forEach { this.add(it) }
            }

            val diffCallback = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    itemList[oldItemPosition].key == newItemsList[newItemPosition].key

                override fun getOldListSize() = itemList.size

                override fun getNewListSize() = newItemsList.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    itemList[oldItemPosition].value == newItemsList[newItemPosition].value
            }

            val diffResult = DiffUtil.calculateDiff(diffCallback)
            itemList = newItemsList
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeanceDayViewHolder =
        SeanceDayViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        )

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: SeanceDayViewHolder, position: Int) {
        with(itemList[position]) {
            holder.scheduleDay.text = DateUtils.formatDateTime(
                holder.containerView.context,
                this.key.timeInMillis,
                DateUtils.FORMAT_SHOW_WEEKDAY or
                    DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_NO_YEAR).capitalize()
            if (DateUtils.isToday(key.timeInMillis)) {
                holder.scheduleDay.setTextColor(ContextCompat.getColor(holder.containerView.context, R.color.colorPrimary))
            }

            val innerAdapter =
                ScheduleWeekInnerListAdapter()
            innerAdapter.items = value
            holder.scheduleInnerList.adapter = innerAdapter
        }
    }

    class SeanceDayViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer
}