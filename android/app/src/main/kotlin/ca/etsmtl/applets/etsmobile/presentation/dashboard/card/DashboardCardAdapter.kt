package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.dashboard.DashboardViewModel

class DashboardCardAdapter(
    private val fragmentManager: FragmentManager,
    private val dashboardViewModel: DashboardViewModel
) : RecyclerView.Adapter<DashboardCardViewHolder>() {
    var items: MutableList<DashboardCard> = mutableListOf()
        set(value) {
            val diffCallback = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return field[oldItemPosition] == value[newItemPosition]
                }

                override fun getOldListSize() = field.size

                override fun getNewListSize() = value.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return field[oldItemPosition] == value[newItemPosition]
                }
            }

            val diffResult = DiffUtil.calculateDiff(diffCallback)

            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dashboard_card, parent, false)

        return DashboardCardViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = items[position].type.ordinal

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DashboardCardViewHolder, position: Int) {
        holder.bind(items[position], fragmentManager)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        dashboardViewModel.moveCard(fromPosition, toPosition)
    }

    fun onItemRemoved(position: Int) {
        dashboardViewModel.removeCard(position)
    }
}