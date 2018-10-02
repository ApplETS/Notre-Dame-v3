package ca.etsmtl.applets.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.di.ViewModelKey
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesFragment
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.applets.etsmobile.presentation.more.MoreFragment
import ca.etsmtl.applets.etsmobile.presentation.more.MoreViewModel
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileFragment
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileViewModel
import ca.etsmtl.applets.etsmobile.presentation.student.StudentFragment
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
    fun contributeStudentFragment(): StudentFragment

    @ContributesAndroidInjector
    fun contributeGradesFragment(): GradesFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesViewModel::class)
    fun bindGradesViewModel(
        gradesViewModel: GradesViewModel
    ): ViewModel

    @ContributesAndroidInjector
    fun contributeProfileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindInfoEtudiantViewModel(
        profileViewModel: ProfileViewModel
    ): ViewModel

    @ContributesAndroidInjector
    fun contributeMoreFragment(): MoreFragment

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    fun bindMoreViewModel(
        moreViewModel: MoreViewModel
    ): ViewModel
}