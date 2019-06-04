package data.repository

import data.db.DashboardCardDatabase
import di.Inject
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext
import model.DashboardCard
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardRepository @Inject constructor(private val database: DashboardCardDatabase) {
    suspend fun dashboardCards(): ReceiveChannel<List<DashboardCard>> {
        return withContext(EtsMobileDispatchers.IO) {
            database.dashboardCards()
        }
    }

    suspend fun updateDashboardCard(card: DashboardCard, position: Int) {
        withContext(EtsMobileDispatchers.IO) {
            database.updateCard(card, position)
        }
    }

    suspend fun restore() = withContext(EtsMobileDispatchers.IO) {
        database.reset()
    }
}
