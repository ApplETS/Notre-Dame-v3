package domain

import data.repository.DashboardCardRepository
import di.Inject
import kotlinx.coroutines.flow.Flow
import model.DashboardCard

/**
 * Created by Sonphil on 01-03-19.
 */

class FetchDashboardCardsUseCase @Inject constructor(private val repository: DashboardCardRepository) {
    suspend operator fun invoke(): Flow<List<DashboardCard>> = repository.dashboardCards()
}