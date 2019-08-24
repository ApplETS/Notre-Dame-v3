package data.db

import kotlinx.coroutines.flow.Flow
import model.DashboardCard

/**
 * Created by Sonphil on 02-06-19.
 */

expect class DashboardCardDatabase {
    /**
     * Returns the dashboard cards ordered according the user preference
     */
    fun dashboardCards(): Flow<List<DashboardCard>>

    /**
     * Update a dashboard card and set to the provided position
     */
    suspend fun updateCard(dashboardCard: DashboardCard, position: Int)

    /**
     * Restore the default cards
     */
    suspend fun reset()
}