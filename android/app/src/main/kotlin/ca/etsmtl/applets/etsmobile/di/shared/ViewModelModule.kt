package ca.etsmtl.applets.etsmobile.di.shared

import dagger.Module
import dagger.Provides
import domain.FetchDashboardCardsUseCase
import domain.RestoreDashboardCardsUseCase
import domain.SaveDashboardCardsUseCase
import presentation.DashboardViewModel

/**
 * Created by Sonphil on 12-02-19.
 */

@Module(includes = [
    UseCaseModule::class
])
object ViewModelModule {
    @JvmStatic
    @Provides
    fun provideDashboardViewModel(
        fetchDashboardCardsUseCase: FetchDashboardCardsUseCase,
        restoreDashboardCardsUseCase: RestoreDashboardCardsUseCase,
        saveDashboardCardsUseCase: SaveDashboardCardsUseCase
    ): DashboardViewModel = DashboardViewModel(
        fetchDashboardCardsUseCase,
        restoreDashboardCardsUseCase,
        saveDashboardCardsUseCase
    )
}