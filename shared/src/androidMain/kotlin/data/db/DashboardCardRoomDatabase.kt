package data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import data.db.dao.DashboardCardDao
import data.db.entity.DashboardCardEntity

/**
 * Created by Sonphil on 04-06-19.
 */

@Database(
    entities = [
        (DashboardCardEntity::class)
    ],
    version = 1
)
abstract class DashboardCardRoomDatabase : RoomDatabase() {
    abstract fun dashboardCardDao(): DashboardCardDao
}