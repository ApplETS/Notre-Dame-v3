package ca.etsmtl.applets.repository.di

import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import dagger.Module
import dagger.Provides

/**
 * Created by Sonphil on 30-12-18.
 */

@Module
class UserModule {
    @Provides
    fun provideSignetsUserCredentials(): SignetsUserCredentials = SignetsUserCredentials.INSTANCE.get()
}