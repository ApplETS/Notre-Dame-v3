package ca.etsmtl.etsmobile.di

import android.app.Application
import ca.etsmtl.etsmobile.di.activitymodule.LoginActivityBuilder
import ca.etsmtl.etsmobile.di.activitymodule.MainActivityBuilder
import ca.etsmtl.etsmobile.presentation.App
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
    NetworkModule::class,
    DatabaseModule::class,
    ViewModelModule::class,
    LoginActivityBuilder::class,
    MainActivityBuilder::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}