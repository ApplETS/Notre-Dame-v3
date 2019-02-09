package data.repository

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import extensions.asChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
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
        val query = dashboardCardQueries.selectAll { type, _, dismissible ->
            DashboardCard(type, dismissible)
        }

        return query.asChannel().map(ioContext) { it.executeAsList() }
    }
}
