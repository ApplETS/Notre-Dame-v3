package domain

import data.repository.DashboardCardRepository
import kotlinx.coroutines.channels.ReceiveChannel
import model.DashboardCard

/**
 * Created by Sonphil on 01-03-19.
 */

class FetchDashboardCardsUseCase(private val repository: DashboardCardRepository) {
    operator fun invoke(): ReceiveChannel<List<DashboardCard>> = repository.dashboardCards()
}