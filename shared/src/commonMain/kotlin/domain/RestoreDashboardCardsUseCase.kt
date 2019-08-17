package domain

import data.repository.DashboardCardRepository
import di.Inject

/**
 * Created by Sonphil on 01-03-19.
 */

class RestoreDashboardCardsUseCase @Inject constructor(private val repository: DashboardCardRepository) {
    operator fun invoke()= repository.restore()
}