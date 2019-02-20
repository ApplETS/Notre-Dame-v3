package data.domain

import data.repository.DashboardCardRepository
import kotlinx.coroutines.channels.ReceiveChannel
import model.DashboardCard

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardsUseCase(private val repository: DashboardCardRepository) {
    fun fetch(): ReceiveChannel<List<DashboardCard>> {
        return repository.dashboardCards()
    }

    fun save(visibleCards: List<DashboardCard>, hiddenCards: List<DashboardCard>) {
        visibleCards.forEachIndexed { index, dashboardCard ->
            repository.updateDashboardCard(dashboardCard, index)
        }

        hiddenCards.forEach { dashboardCard ->
            repository.updateDashboardCard(dashboardCard, -1)
        }
    }

    fun restore() = repository.restore()
}