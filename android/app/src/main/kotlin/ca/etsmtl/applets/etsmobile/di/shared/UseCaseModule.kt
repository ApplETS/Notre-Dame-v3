package ca.etsmtl.applets.etsmobile.di.shared

import dagger.Module
import dagger.Provides
import data.repository.DashboardCardRepository
import domain.FetchDashboardCardsUseCase
import domain.RestoreDashboardCardsUseCase
import domain.SaveDashboardCardsUseCase
import javax.inject.Singleton

/**
 * Created by Sonphil on 09-02-19.
 */

@Module(includes = [
    RepositoryModule::class
])
object UseCaseModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideFetchDashboardCardsUseCase(
        repository: DashboardCardRepository
    ): FetchDashboardCardsUseCase {
        return FetchDashboardCardsUseCase(repository)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideRestoreDashboardCardsUseCase(
        repository: DashboardCardRepository
    ): RestoreDashboardCardsUseCase {
        return RestoreDashboardCardsUseCase(repository)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideSaveDashboardCardsUseCase(
        repository: DashboardCardRepository
    ): SaveDashboardCardsUseCase {
        return SaveDashboardCardsUseCase(repository)
    }
}