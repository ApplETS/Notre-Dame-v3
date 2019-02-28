package ca.etsmtl.applets.etsmobile.di.shared

import dagger.Module
import dagger.Provides
import data.domain.DashboardCardsUseCase
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
        dashboardUseCase: DashboardCardsUseCase
    ): DashboardViewModel = DashboardViewModel(dashboardUseCase)
}