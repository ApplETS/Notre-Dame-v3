package ca.etsmtl.applets.etsmobile.di.shared

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import data.db.DashboardCardRoomDatabase
import data.db.GitHubRoomDatabase
import data.db.dao.DashboardCardDao
import data.db.dao.GitHubContributorDao
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
        private lateinit var DASHBOARD_CARDS_DB_INSTANCE: DashboardCardRoomDatabase

        @JvmStatic
        @Singleton
        @Provides
        fun provideDashboardCardRoomDb(context: Context): DashboardCardRoomDatabase {
            DASHBOARD_CARDS_DB_INSTANCE = Room.databaseBuilder(
                context,
                DashboardCardRoomDatabase::class.java,
                "etsmobiledashboardcard.db"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        initDashboardCardDbContent(DASHBOARD_CARDS_DB_INSTANCE)
                    }
                })
                .build()

            return DASHBOARD_CARDS_DB_INSTANCE
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

        @JvmStatic
        @Singleton
        @Provides
        fun provideGitHubRoomDb(context: Context): GitHubRoomDatabase {
            return Room.databaseBuilder(
                context,
                GitHubRoomDatabase::class.java,
                "etsmobilegithub.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideGitHubContributorDao(db: GitHubRoomDatabase): GitHubContributorDao = db.contributorsDao()
    }
}