package ca.etsmtl.applets.etsmobile.di.shared

import dagger.Module
import dagger.Provides
import data.domain.FetchDashboardCardsUseCase
import data.repository.DashboardCardRepository
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
}