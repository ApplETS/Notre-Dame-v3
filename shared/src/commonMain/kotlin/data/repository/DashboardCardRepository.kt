package data.repository

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import model.DashboardCard
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardRepository(private val dashboardCardQueries: DashboardCardQueries) {
    fun dashboardCards(): ReceiveChannel<List<DashboardCard>> = GlobalScope.produce(
        EtsMobileDispatchers.IO,
        1
    ) {
        val query = dashboardCardQueries.selectAll { type, _, visible, dismissible ->
            DashboardCard(type, visible, dismissible)
        }

        offer(query.executeAsList())
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
