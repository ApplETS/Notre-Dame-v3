package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.dashboard.DashboardViewModel

class DashboardCardAdapter(
    private val fragmentManager: FragmentManager,
    private val dashboardViewModel: DashboardViewModel
) : RecyclerView.Adapter<DashboardCardViewHolder>() {
    var items: List<DashboardCard> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
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

        notifyItemMoved(fromPosition, toPosition)
    }

    fun onItemRemoved(position: Int) {
        dashboardViewModel.removeCard(position)

        notifyItemRemoved(position)
    }
}