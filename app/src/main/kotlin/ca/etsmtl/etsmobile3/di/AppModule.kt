package ca.etsmtl.etsmobile3.di

import android.app.Application
import android.content.Context
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.repository.InfoEtudiantRepository
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

    @Singleton @Provides @JvmStatic
    fun provideInfoEtudiantRepository(
            signetsApi: SignetsApi
    ): InfoEtudiantRepository = InfoEtudiantRepository(signetsApi)
}
