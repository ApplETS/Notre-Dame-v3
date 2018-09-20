package ca.etsmtl.etsmobile.di.activitymodule

import ca.etsmtl.etsmobile.presentation.grades.GradesDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sonphil on 19-09-18.
 */
@Module
interface GradesDetailsActivityBuilder {
    @ContributesAndroidInjector(modules = [GradesDetailsActivityModule::class])
    fun contributeGradesDetailsActivity(): GradesDetailsActivity
}