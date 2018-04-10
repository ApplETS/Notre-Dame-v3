package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.profile.infoetudiant.InfoEtudiantFragment
import ca.etsmtl.etsmobile.presentation.profile.infoetudiant.InfoEtudiantViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Sonphil on 15-03-18.
 */
@Module
interface MainActivityModule {
    @ContributesAndroidInjector
    fun contributeInfoEtudiantFragment(): InfoEtudiantFragment

    @Binds
    @IntoMap
    @ViewModelKey(InfoEtudiantViewModel::class)
    fun bindInfoEtudiantViewModel(
        infoEtudiantViewModel: InfoEtudiantViewModel
    ): ViewModel
}