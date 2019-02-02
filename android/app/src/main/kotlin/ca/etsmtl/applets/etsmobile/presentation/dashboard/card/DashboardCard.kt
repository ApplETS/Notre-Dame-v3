package ca.etsmtl.applets.etsmobile.presentation.dashboard.card

data class DashboardCard(
    val type: DashboardCardType,
    val isDismissible: Boolean = true
)