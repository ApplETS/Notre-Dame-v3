package ca.etsmtl.applets.etsmobile.di.shared

import android.app.Application
import dagger.Module
import dagger.Provides
import data.repository.DashboardCardRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Sonphil on 09-02-19.
 */

@Module
object RepositoryModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideDashboardCardRepository(application: Application): DashboardCardRepository {
        val db = DbComponent.builder()
            .context(application)
            .fileName("etsmobile.shated.db")
            .build()
            .etsMobileDb()

        return DashboardCardRepository(Dispatchers.IO, db.dashboardCardQueries)
    }
}