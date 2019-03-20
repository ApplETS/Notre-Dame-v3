package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import android.graphics.Canvas
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.extension.dpToPx
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

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        viewHolder.itemView.adjustItemViewElevation(isCurrentlyActive)
    }

    private fun View.adjustItemViewElevation(isCurrentlyActive: Boolean) {
        val elevationDp = if (isCurrentlyActive) 8 else 1

        ViewCompat.setElevation(this, elevationDp.dpToPx(resources.displayMetrics))
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