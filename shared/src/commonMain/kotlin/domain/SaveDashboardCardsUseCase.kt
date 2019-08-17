package domain

import data.repository.DashboardCardRepository
import di.Inject
import model.DashboardCard

/**
 * Created by Sonphil on 01-03-19.
 */

class SaveDashboardCardsUseCase @Inject constructor(private val repository: DashboardCardRepository) {
    operator fun invoke(visibleCards: List<DashboardCard>, hiddenCards: List<DashboardCard>) {
        visibleCards.forEachIndexed { index, dashboardCard ->
            repository.updateDashboardCard(dashboardCard, index)
        }

        hiddenCards.forEach { dashboardCard ->
            repository.updateDashboardCard(dashboardCard, -1)
        }
    }
}