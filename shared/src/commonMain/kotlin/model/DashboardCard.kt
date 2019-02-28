package model

data class DashboardCard(
    val type: Type,
    var visible: Boolean,
    val dismissible: Boolean = true
) {
    enum class Type {
        DASHBOARD_CARD_APPLETS,
        DASHBOARD_CARD_TODAY_SCHEDULE,
        DASHBOARD_CARD_GRADES;
    }
}