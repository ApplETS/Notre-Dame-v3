package domain

import data.repository.DashboardCardRepository
import di.Inject
import kotlinx.coroutines.channels.ReceiveChannel
import model.DashboardCard

/**
 * Created by Sonphil on 01-03-19.
 */

class FetchDashboardCardsUseCase @Inject constructor(private val repository: DashboardCardRepository) {
    suspend operator fun invoke(): ReceiveChannel<List<DashboardCard>> = repository.dashboardCards()
}