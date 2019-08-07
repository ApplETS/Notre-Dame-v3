package ca.etsmtl.applets.repository.di

import dagger.Module
import dagger.Provides
import model.UserCredentials

/**
 * Created by Sonphil on 30-12-18.
 */

@Module
class UserModule {
    @Provides
    fun provideSignetsUserCredentials(): UserCredentials = UserCredentials.INSTANCE!!
}