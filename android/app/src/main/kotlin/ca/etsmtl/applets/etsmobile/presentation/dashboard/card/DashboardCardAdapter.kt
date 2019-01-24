package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R

class DashboardCardAdapter : RecyclerView.Adapter<DashboardCardViewHolder>() {
    private var items: List<DashboardCard> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dashboard_card, parent, false)

        return DashboardCardViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = items[position].type.ordinal

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DashboardCardViewHolder, position: Int) {
        holder.bind(items[position])
    }
}