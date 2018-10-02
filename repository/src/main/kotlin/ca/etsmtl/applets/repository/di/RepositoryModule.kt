package ca.etsmtl.repository.di

import dagger.Module

/**
 * Created by Sonphil on 07-06-18.
 */

@Module(includes = [
    DatabaseModule::class,
    NetworkModule::class
])
class RepositoryModule {
    companion object {
        val instance = RepositoryModule()
    }
}