package ca.etsmtl.applets.etsmobile.di.shared

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import data.db.DashboardCardDatabase
import data.db.DashboardCardDatabaseImpl
import data.db.DashboardCardRoomDatabase
import data.db.dao.DashboardCardDao
import data.db.entity.DashboardCardEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.DashboardCard
import utils.EtsMobileDispatchers
import javax.inject.Singleton

/**
 * Created by Sonphil on 09-02-19.
 */

@Module(includes = [DbModule.Providers::class])
internal abstract class DbModule {
    @Module
    internal object Providers {
        private lateinit var DB_INSTANCE: DashboardCardRoomDatabase

        @JvmStatic
        @Singleton
        @Provides
        fun provideDashboardCardRoomDb(context: Context): DashboardCardRoomDatabase {
            DB_INSTANCE = Room.databaseBuilder(
                context,
                DashboardCardRoomDatabase::class.java,
                "etsmobiledashboardcard.db"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        initDashboardCardDbContent(DB_INSTANCE)
                    }
                })
                .build()

            return DB_INSTANCE
        }

        private fun initDashboardCardDbContent(db: DashboardCardRoomDatabase) {
            CoroutineScope(EtsMobileDispatchers.IO).launch {
                runBlocking {
                    db.dashboardCardDao().let { dao ->
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
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideDashboardCardDao(db: DashboardCardRoomDatabase): DashboardCardDao = db.dashboardCardDao()
    }

    @Binds
    abstract fun provideDashboardCardDatabase(impl: DashboardCardDatabaseImpl): DashboardCardDatabase
}