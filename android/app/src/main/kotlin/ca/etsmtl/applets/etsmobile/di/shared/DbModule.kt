package ca.etsmtl.applets.etsmobile.di.shared

import android.content.Context
import ca.etsmtl.applets.shared.db.DashboardCardQueries
import ca.etsmtl.applets.shared.db.EtsMobileDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import data.db.Db
import javax.inject.Singleton

/**
 * Created by Sonphil on 09-02-19.
 */

@Module(includes = [DbModule.Providers::class])
internal object DbModule {
    @Module
    internal object Providers {
        @JvmStatic
        @Provides
        @Singleton
        fun provideEtsMobileDb(context: Context): EtsMobileDb {
            Db.setupDb(AndroidSqliteDriver(EtsMobileDb.Schema, context, "etsmobile.shared.db"))

            return Db.instance
        }

        @JvmStatic
        @Provides
        fun dashboardCardQueries(db: EtsMobileDb): DashboardCardQueries {
            return db.dashboardCardQueries
        }
    }
}