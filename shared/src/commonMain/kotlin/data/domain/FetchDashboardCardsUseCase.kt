package data.domain

import data.repository.DashboardCardRepository
import kotlinx.coroutines.channels.ReceiveChannel
import model.DashboardCard

/**
 * Created by Sonphil on 09-02-19.
 */

class FetchDashboardCardsUseCase(private val dashboardCardRepository: DashboardCardRepository) {
    operator fun invoke() : ReceiveChannel<List<DashboardCard>> {
        return dashboardCardRepository.dashboardCards()
    }
}