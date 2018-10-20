package ca.etsmtl.applets.etsmobile.di.activitymodule

import ca.etsmtl.applets.etsmobile.presentation.about.AboutActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sonphil on 01-07-18.
 */
@Module
interface AboutActivityBuilder {
    @ContributesAndroidInjector
    fun contributeAboutActivity(): AboutActivity
}