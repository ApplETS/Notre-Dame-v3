package ca.etsmtl.etsmobile.di.activitymodule

import ca.etsmtl.etsmobile.presentation.grades.GradesDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sonphil on 15-08-18.
 */
@Module
interface GradesDetailsModule {
    @ContributesAndroidInjector
    fun contributeGradesDetailsFragment(): GradesDetailsFragment
}