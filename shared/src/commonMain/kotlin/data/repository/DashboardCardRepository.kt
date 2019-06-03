package data.repository

import data.db.DashboardCardDatabase
import di.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DashboardCard
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardRepository @Inject constructor(private val db: DashboardCardDatabase) {
    suspend fun dashboardCards(): ReceiveChannel<List<DashboardCard>> {
        return withContext(EtsMobileDispatchers.IO) {
            db.dashboardCards()
        }
    }

    fun updateDashboardCard(card: DashboardCard, position: Int) = CoroutineScope(EtsMobileDispatchers.IO)
        .launch {
            db.updateCard(card, position)
        }

    fun restore() = CoroutineScope(EtsMobileDispatchers.IO)
        .launch {
            db.reset()
        }
}
