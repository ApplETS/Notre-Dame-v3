package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import presentation.DashboardViewModel

class DashboardCardsTouchHelperCallback(
    private val dashboardViewModel: DashboardViewModel,
    private val fragmentManager: FragmentManager
) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val card = (viewHolder as DashboardCardViewHolder).card

        if (card == null || !card.dismissible) {
            return 0
        }

        return super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        dashboardViewModel.onCardMoved(viewHolder.adapterPosition, target.adapterPosition)

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        (viewHolder as DashboardCardViewHolder).clear(fragmentManager)

        dashboardViewModel.onCardRemoved(viewHolder.adapterPosition)
    }
}