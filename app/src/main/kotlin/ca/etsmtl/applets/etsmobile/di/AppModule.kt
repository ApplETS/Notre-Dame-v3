package ca.etsmtl.applets.etsmobile.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
internal object AppModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    @JvmStatic
    fun provideApp(application: Application): App = application as App

    @Singleton
    @Provides
    @JvmStatic
    fun providePrefs(application: Application): SharedPreferences = application
            .getSharedPreferences(application.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE)

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserCredentials(): SignetsUserCredentials = SignetsUserCredentials.INSTANCE.get()
}
