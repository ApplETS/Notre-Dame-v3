package data.repository

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import di.Inject
import extension.asChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DashboardCard
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardRepository @Inject constructor(private val dashboardCardQueries: DashboardCardQueries) {
    suspend fun dashboardCards(): ReceiveChannel<List<DashboardCard>> {
        return withContext(EtsMobileDispatchers.IO) {
            dashboardCardQueries
                .selectAll { type, _, visible, dismissible ->
                    DashboardCard(type, visible, dismissible)
                }
                .asChannel()
                .map(EtsMobileDispatchers.IO) { query ->
                    query.executeAsList()
                }
        }
    }

    fun updateDashboardCard(card: DashboardCard, position: Int) = CoroutineScope(EtsMobileDispatchers.IO)
        .launch {
            dashboardCardQueries.updateCard(position.toLong(), card.visible, card.type)
        }

    fun restore() {
        dashboardCardQueries.deleteAll()
        dashboardCardQueries.insertInitialCards()
    }
}
