package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.gradesdetails.GradesDetailsFragment
import ca.etsmtl.etsmobile.presentation.gradesdetails.GradesDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Sonphil on 15-08-18.
 */
@Module
interface GradesDetailsActivityModule {
    @ContributesAndroidInjector
    fun contributeGradesDetailsFragment(): GradesDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesDetailsViewModel::class)
    fun bindGradesViewModel(gradesDetailsViewModel: GradesDetailsViewModel): ViewModel
}