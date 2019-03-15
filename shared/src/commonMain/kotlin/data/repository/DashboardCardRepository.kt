package data.repository

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import extension.asChannel
import di.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import model.DashboardCard
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 09-02-19.
 */

class DashboardCardRepository @Inject constructor(private val dashboardCardQueries: DashboardCardQueries) {
    fun dashboardCards(): ReceiveChannel<List<DashboardCard>> = GlobalScope.produce(
        EtsMobileDispatchers.IO,
        1
    ) {
        dashboardCardQueries
            .selectAll { type, _, visible, dismissible ->
                DashboardCard(type, visible, dismissible)
            }
            .asChannel()
            .map(EtsMobileDispatchers.IO) { query ->
                query.executeAsList()
            }
            .consumeEach { cards ->
                offer(cards)
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
