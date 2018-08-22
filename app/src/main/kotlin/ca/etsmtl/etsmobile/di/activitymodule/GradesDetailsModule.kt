package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.grades.GradesDetailsFragment
import ca.etsmtl.etsmobile.presentation.grades.GradesDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Sonphil on 15-08-18.
 */
@Module
interface GradesDetailsModule {
    @ContributesAndroidInjector
    fun contributeGradesDetailsFragment(): GradesDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesDetailsViewModel::class)
    fun bindGradesViewModel(
        gradesDetailsViewModel: GradesDetailsViewModel
    ): ViewModel
}