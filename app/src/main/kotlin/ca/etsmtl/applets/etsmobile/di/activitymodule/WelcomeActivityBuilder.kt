package ca.etsmtl.applets.etsmobile.di.activitymodule

import ca.etsmtl.applets.etsmobile.presentation.login.WelcomeActivity
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