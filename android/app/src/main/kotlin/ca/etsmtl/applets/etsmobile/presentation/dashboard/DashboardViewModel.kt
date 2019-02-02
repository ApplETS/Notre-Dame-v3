package ca.etsmtl.applets.etsmobile.presentation.dashboard

import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCard
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardType
import javax.inject.Inject

class DashboardViewModel @Inject constructor() : ViewModel() {
    val cards = listOf(
        DashboardCard(DashboardCardType.DASHBOARD_CARD_APPLETS),
        DashboardCard(DashboardCardType.DASHBOARD_CARD_TODAY_SCHEDULE)
    )
}