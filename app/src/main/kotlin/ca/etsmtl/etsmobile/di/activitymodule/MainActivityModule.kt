package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.more.MoreFragment
import ca.etsmtl.etsmobile.presentation.more.MoreViewModel
import ca.etsmtl.etsmobile.presentation.profile.ProfileFragment
import ca.etsmtl.etsmobile.presentation.profile.ProfileViewModel
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
    fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun contributeMoreFragment(): MoreFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindInfoEtudiantViewModel(
        profileViewModel: ProfileViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    fun bindMoreViewModel(
        moreViewModel: MoreViewModel
    ): ViewModel
}