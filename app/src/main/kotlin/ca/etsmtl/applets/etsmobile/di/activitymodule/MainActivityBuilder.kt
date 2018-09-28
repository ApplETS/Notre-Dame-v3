package ca.etsmtl.applets.etsmobile.di.activitymodule

import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sonphil on 15-03-18.
 */
@Module
interface MainActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity
}