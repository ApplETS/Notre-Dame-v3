package ca.etsmtl.applets.etsmobile.di.shared

import dagger.Binds
import dagger.Module
import data.db.DashboardCardDatabase
import data.db.DashboardCardDatabaseImpl

/**
 * Created by Sonphil on 09-02-19.
 */

@Module(includes = [DbModule.Providers::class])
internal abstract class DbModule {
    @Binds
    abstract fun provideDashboardCardDatabase(impl: DashboardCardDatabaseImpl): DashboardCardDatabase

    @Module
    internal object Providers {

    }
}