package data.domain

import data.repository.DashboardCardRepository
import kotlinx.coroutines.channels.ReceiveChannel
import model.DashboardCard

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardsUseCase(private val dashboardCardRepository: DashboardCardRepository) {
    fun fetch(): ReceiveChannel<List<DashboardCard>> {
        return dashboardCardRepository.dashboardCards()
    }

    fun save(visibleCards: List<DashboardCard>, hiddenCards: List<DashboardCard>) {
        visibleCards.forEachIndexed { index, dashboardCard ->
            dashboardCardRepository.updateDashboardCard(dashboardCard, index)
        }

        hiddenCards.forEach { dashboardCard ->
            dashboardCardRepository.updateDashboardCard(dashboardCard, -1)
        }
    }
}