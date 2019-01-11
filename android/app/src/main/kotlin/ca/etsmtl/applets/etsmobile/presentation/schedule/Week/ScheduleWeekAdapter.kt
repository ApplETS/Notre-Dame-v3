package ca.etsmtl.applets.etsmobile.presentation.schedule.Week

import android.graphics.Typeface
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.repository.data.model.Seance
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*
import java.util.Date

/**
 * Created by mykaelll87 on 2018-10-28
 */
class ScheduleWeekAdapter : RecyclerView.Adapter<ScheduleWeekAdapter.SeanceDayViewHolder>() {

    private var itemList: List<Map.Entry<Date, List<Seance>>> = emptyList()
    var items: Map<Date, List<Seance>> = emptyMap()
        set(value) {
            field = value
            val newItemsList = mutableListOf<Map.Entry<Date, List<Seance>>>().apply {
                value.forEach { this.add(it) }
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
                this.key.time,
                DateUtils.FORMAT_SHOW_WEEKDAY or
                    DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_NO_YEAR)
            if (DateUtils.isToday(key.time)) {
                holder.scheduleDay.setTypeface(holder.scheduleDay.typeface, Typeface.ITALIC)
                holder.scheduleDay.setTextColor(ContextCompat.getColor(holder.containerView.context, R.color.material_light_white))
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