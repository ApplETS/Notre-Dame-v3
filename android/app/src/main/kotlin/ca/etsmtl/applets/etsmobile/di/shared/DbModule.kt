package ca.etsmtl.applets.etsmobile.di.shared

import dagger.Module
import dagger.Provides
import data.db.DashboardCardDatabase
import data.db.DashboardCardDatabaseImpl
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
        fun provideDashboardCardDatabase(): DashboardCardDatabase {
            return DashboardCardDatabaseImpl()
        }
    }
}