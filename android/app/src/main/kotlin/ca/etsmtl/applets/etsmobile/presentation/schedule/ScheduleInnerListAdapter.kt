package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.repository.data.model.Seance
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sub_item_schedule.*

/**
Created by mykaelll87 on 08/12/18
 */
class ScheduleInnerListAdapter : RecyclerView.Adapter<ScheduleInnerListAdapter.SeanceViewHolder>() {

    var items: List<Seance> = emptyList()
        set(value) {
            val diffCallback = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    field[oldItemPosition] == value[newItemPosition]

                override fun getOldListSize(): Int = field.size

                override fun getNewListSize(): Int = value.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    field[oldItemPosition] == value[newItemPosition]
            }
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeanceViewHolder = SeanceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.sub_item_schedule, parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SeanceViewHolder, position: Int) {
        with(items[position]) {
            holder.textViewScheduleTitreCours.text = libelleCours
            holder.textViewScheduleSigleGroup.text = "$sigleCours-$groupe"
            holder.textViewScheduleType.text = nomActivite
            holder.textViewScheduleLocal.text = local
            holder.textViewScheduleStartTime.text = DateUtils
                .formatDateTime(
                    holder.containerView.context,
                    dateDebut.time,
                    DateUtils.FORMAT_SHOW_TIME
                )
            holder.textViewScheduleEndTime.text = DateUtils
                .formatDateTime(
                    holder.containerView.context,
                    dateFin.time,
                    DateUtils.FORMAT_SHOW_TIME
                )
        }
    }

    class SeanceViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}