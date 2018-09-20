package ca.etsmtl.etsmobile.di

import android.app.Application
import ca.etsmtl.etsmobile.di.activitymodule.AboutActivityBuilder
import ca.etsmtl.etsmobile.di.activitymodule.GradesDetailsActivityBuilder
import ca.etsmtl.etsmobile.di.activitymodule.MainActivityBuilder
import ca.etsmtl.etsmobile.di.activitymodule.WelcomeActivityBuilder
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.repository.di.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Sonphil on 28-02-18.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    WelcomeActivityBuilder::class,
    MainActivityBuilder::class,
    AboutActivityBuilder::class,
    GradesDetailsActivityBuilder::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}