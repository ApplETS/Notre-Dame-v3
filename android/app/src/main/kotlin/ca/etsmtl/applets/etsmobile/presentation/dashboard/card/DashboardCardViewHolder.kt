package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.applets.AppletsCardFragment
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule.TodayScheduleCardFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dashboard_card.view.container

class DashboardCardViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(card: DashboardCard, fragmentManager: FragmentManager) {
        val fragment = when (card.type) {
            DashboardCardType.DASHBOARD_CARD_APPLETS -> AppletsCardFragment.newInstance()
            DashboardCardType.DASHBOARD_CARD_TODAY_SCHEDULE -> TodayScheduleCardFragment.newInstance()
        }

        fragmentManager.replaceFragment(fragment)
    }

    private fun FragmentManager.replaceFragment(fragment: Fragment) {
        transaction {
            itemView.container?.let {
                findFragmentById(it.id)?.let { remove(it) }

                val newId = View.generateViewId()

                it.id = newId
                replace(newId, fragment)
            }
        }
    }
}