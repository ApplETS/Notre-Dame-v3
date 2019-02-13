package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.applets.AppletsCardFragment
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades.GradesCardFragment
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule.TodayScheduleCardFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dashboard_card.view.container
import model.DashboardCard

class DashboardCardViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    var card: DashboardCard? = null
        private set

    fun bind(card: DashboardCard, fragmentManager: FragmentManager) {
        this.card = card

        val fragment = fragmentManager.findOrCreateFragment(card.type)

        fragmentManager.replaceFragment(fragment, card.type.name)
    }

    private fun FragmentManager.findOrCreateFragment(type: DashboardCard.Type): Fragment {
        return findFragmentByTag(type.name) ?: when (type) {
            DashboardCard.Type.DASHBOARD_CARD_APPLETS -> AppletsCardFragment.newInstance()
            DashboardCard.Type.DASHBOARD_CARD_TODAY_SCHEDULE -> TodayScheduleCardFragment.newInstance()
            DashboardCard.Type.DASHBOARD_CARD_GRADES -> GradesCardFragment.newInstance()
        }
    }

    private fun FragmentManager.replaceFragment(fragment: Fragment, tag: String) {
        transaction(now = true) {
            remove(fragment)
        }

        transaction {
            itemView.container?.let { itemView ->
                findFragmentById(itemView.id)?.let { remove(it) }

                itemView.id = View.generateViewId()
                replace(itemView.id, fragment, tag)
            }
        }
    }
}