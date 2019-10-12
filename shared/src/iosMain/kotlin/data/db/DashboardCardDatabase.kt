package data.db

import kotlinx.coroutines.flow.Flow
import model.DashboardCard

/**
 * Created by Sonphil on 02-06-19.
 */

actual class DashboardCardDatabase {
    /**
     * Returns the dashboard cards ordered according the user preference
     */
    actual fun dashboardCards(): Flow<List<DashboardCard>> {
        TODO("not implemented")
    }

    /**
     * Update a dashboard card and set to the provided position
     */
    actual suspend fun updateCard(dashboardCard: DashboardCard, position: Int) {
        TODO("not implemented")
    }

    /**
     * Restore the default cards
     */
    actual suspend fun reset() {
        TODO("not implemented")
    }
}