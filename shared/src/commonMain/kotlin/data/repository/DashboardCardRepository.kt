package data.repository

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import model.DashboardCard
import kotlin.coroutines.CoroutineContext

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardRepository(
    private val ioContext: CoroutineContext,
    private val dashboardCardQueries: DashboardCardQueries
) {
    fun dashboardCards(): ReceiveChannel<List<DashboardCard>> = GlobalScope.produce(
        ioContext,
        1
    ) {
        val query = dashboardCardQueries.selectAll { type, _, visible, dismissible ->
            DashboardCard(type, visible, dismissible)
        }

        offer(query.executeAsList())
    }

    fun updateDashboardCard(card: DashboardCard, position: Int) = CoroutineScope(ioContext)
        .launch {
            dashboardCardQueries.updateCard(position.toLong(), card.visible, card.type)
        }

    fun restore() {
        dashboardCardQueries.deleteAll()
        dashboardCardQueries.insertInitialCards()
    }
}
