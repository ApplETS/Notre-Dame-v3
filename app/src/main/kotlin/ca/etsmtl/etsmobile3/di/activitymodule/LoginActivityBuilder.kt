package ca.etsmtl.etsmobile3.di.activitymodule

import ca.etsmtl.etsmobile3.presentation.login.LoginActivity
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