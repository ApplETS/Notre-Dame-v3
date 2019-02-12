package ca.etsmtl.applets.etsmobile.di.shared

import ca.etsmtl.applets.shared.db.DashboardCardQueries
import dagger.Module
import dagger.Provides
import data.repository.DashboardCardRepository
import javax.inject.Singleton

/**
 * Created by Sonphil on 09-02-19.
 */

@Module(includes = [DbModule::class])
object RepositoryModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideDashboardCardRepository(queries: DashboardCardQueries): DashboardCardRepository {
            return DashboardCardRepository(queries)
    }
}