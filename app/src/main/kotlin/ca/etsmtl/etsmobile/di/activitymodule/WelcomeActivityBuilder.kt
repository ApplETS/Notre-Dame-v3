package ca.etsmtl.etsmobile.di.activitymodule

import ca.etsmtl.etsmobile.presentation.login.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
interface WelcomeActivityBuilder {
    @ContributesAndroidInjector(modules = [WelcomeActivityModule::class])
    fun contributeWelcomeActivity(): WelcomeActivity
}