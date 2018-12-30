package ca.etsmtl.applets.repository.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ca.etsmtl.applets.repository.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sonphil on 07-06-18.
 */

@Module(includes = [
    DatabaseModule::class,
    NetworkModule::class,
    UserModule::class
])
class RepositoryModule(private val application: Application) {
    @Singleton
    @Provides
    fun providePrefs(): SharedPreferences = application
        .getSharedPreferences(application.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
}