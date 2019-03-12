package ca.etsmtl.applets.etsmobile.di.shared

import android.content.Context
import ca.etsmtl.applets.shared.db.DashboardCardQueries
import ca.etsmtl.applets.shared.db.EtsMobileDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import extension.createDb
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
            val driver = AndroidSqliteDriver(
                EtsMobileDb.Schema,
                context,
                "etsmobile.shared.db"
            )

            return driver.createDb()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun dashboardCardQueries(db: EtsMobileDb): DashboardCardQueries {
            return db.dashboardCardQueries
        }
    }
}