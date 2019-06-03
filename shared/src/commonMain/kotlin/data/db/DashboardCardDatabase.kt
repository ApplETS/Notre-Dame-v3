package data.db

import kotlinx.coroutines.channels.Channel
import model.DashboardCard

/**
 * Created by Sonphil on 02-06-19.
 */

interface DashboardCardDatabase {
    suspend fun dashboardCards(): Channel<List<DashboardCard>>
    suspend fun updateCard(dashboardCard: DashboardCard, position: Int)
    suspend fun reset()
}