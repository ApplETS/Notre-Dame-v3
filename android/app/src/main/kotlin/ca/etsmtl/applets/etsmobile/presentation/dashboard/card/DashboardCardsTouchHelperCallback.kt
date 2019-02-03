package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DashboardCardsTouchHelperCallback(private val dashboardCardAdapter: DashboardCardAdapter) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        dashboardCardAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        dashboardCardAdapter.onItemRemoved(viewHolder.adapterPosition)
    }
}