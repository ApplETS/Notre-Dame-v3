package data.db

import data.db.dao.DashboardCardDao
import data.db.entity.DashboardCardEntity
import data.db.entity.mapper.toDashboardCardEntity
import data.db.entity.mapper.toDashboardCards
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.reactive.openSubscription
import kotlinx.coroutines.runBlocking
import model.DashboardCard
import javax.inject.Inject

/**
 * Created by Sonphil on 02-06-19.
 */

class DashboardCardDatabaseImpl @Inject constructor(
    private val dao: DashboardCardDao
) : DashboardCardDatabase {
    override suspend fun dashboardCards(): ReceiveChannel<List<DashboardCard>> {
        return dao.getAll().map {
            it.toDashboardCards()
        }.openSubscription()
    }

    override suspend fun updateCard(dashboardCard: DashboardCard, position: Int) {
        dao.updateDashboardCard(dashboardCard.toDashboardCardEntity(position))
    }

    override suspend fun reset() {
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