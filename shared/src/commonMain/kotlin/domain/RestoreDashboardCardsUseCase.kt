package domain

import data.repository.DashboardCardRepository

/**
 * Created by Sonphil on 01-03-19.
 */

class RestoreDashboardCardsUseCase(private val repository: DashboardCardRepository) {
    operator fun invoke()= repository.restore()
}