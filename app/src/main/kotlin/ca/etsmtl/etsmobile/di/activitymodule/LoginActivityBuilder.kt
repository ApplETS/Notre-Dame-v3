package ca.etsmtl.etsmobile.di.activitymodule

import ca.etsmtl.etsmobile.presentation.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
interface LoginActivityBuilder {
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    fun contributeLoginActivity(): LoginActivity
}