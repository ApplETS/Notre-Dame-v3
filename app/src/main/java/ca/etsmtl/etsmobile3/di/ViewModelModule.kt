package ca.etsmtl.etsmobile3.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}