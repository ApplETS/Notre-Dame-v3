package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.infoetudiant.InfoEtudiantFragment
import ca.etsmtl.etsmobile.presentation.infoetudiant.InfoEtudiantViewModel
import ca.etsmtl.etsmobile.presentation.more.MoreFragment
import ca.etsmtl.etsmobile.presentation.more.MoreViewModel
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

    @ContributesAndroidInjector
    fun contributeMoreFragment(): MoreFragment

    @Binds
    @IntoMap
    @ViewModelKey(InfoEtudiantViewModel::class)
    fun bindInfoEtudiantViewModel(
        infoEtudiantViewModel: InfoEtudiantViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    fun bindMoreViewModel(
        moreViewModel: MoreViewModel
    ): ViewModel
}