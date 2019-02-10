package data.repository

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import extensions.asChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
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
    fun dashboardCards(): ReceiveChannel<List<DashboardCard>> {
        val query = dashboardCardQueries.selectAll { type, _, visible, dismissible ->
            DashboardCard(type, visible, dismissible)
        }

        return query.asChannel().map(ioContext) { it.executeAsList() }
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
