package data.db

import kotlinx.coroutines.channels.Channel
import model.DashboardCard

/**
 * Created by Sonphil on 02-06-19.
 */

class DashboardCardDatabaseImpl : DashboardCardDatabase {
    override suspend fun dashboardCards(): Channel<List<DashboardCard>> {
        TODO()
    }

    override suspend fun updateCard(dashboardCard: DashboardCard, position: Int) {
        TODO()
    }

    override suspend fun reset() {
        TODO()
    }
}