package data.db

import data.db.dao.DashboardCardDao
import data.db.entity.DashboardCardEntity
import data.db.entity.mapper.toDashboardCardEntity
import data.db.entity.mapper.toDashboardCards
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import model.DashboardCard
import javax.inject.Inject

/**
 * Implementation of the expected [DashboardCardDatabase]
 *
 * Created by Sonphil on 02-06-19.
 */

actual class DashboardCardDatabase @Inject constructor(
    private val dao: DashboardCardDao
) {
    actual fun dashboardCards(): Flow<List<DashboardCard>> = dao.getAll()
        .map { entities ->
            entities.toDashboardCards()
        }

    actual suspend fun updateCard(dashboardCard: DashboardCard, position: Int) {
        dao.updateDashboardCard(dashboardCard.toDashboardCardEntity(position))
    }

    actual suspend fun reset() {
        runBlocking {
            dao.deleteAll()
            dao.insertDashboardCard(DashboardCardEntity(
                DashboardCard.Type.DASHBOARD_CARD_APPLETS.name,
                true,
                DashboardCard.Type.DASHBOARD_CARD_APPLETS.ordinal
            ))
            dao.insertDashboardCard(DashboardCardEntity(
                DashboardCard.Type.DASHBOARD_CARD_TODAY_SCHEDULE.name,
                true,
                DashboardCard.Type.DASHBOARD_CARD_TODAY_SCHEDULE.ordinal
            ))
            dao.insertDashboardCard(DashboardCardEntity(
                DashboardCard.Type.DASHBOARD_CARD_GRADES.name,
                true,
                DashboardCard.Type.DASHBOARD_CARD_GRADES.ordinal
            ))
        }
    }
}