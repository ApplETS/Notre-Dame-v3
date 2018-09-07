package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.gradesdetails.GradesDetailsFragment
import ca.etsmtl.etsmobile.presentation.gradesdetails.GradesDetailsViewModel
import ca.etsmtl.etsmobile.presentation.grades.GradesFragment
import ca.etsmtl.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.etsmobile.presentation.student.StudentViewModel
import ca.etsmtl.etsmobile.presentation.more.MoreFragment
import ca.etsmtl.etsmobile.presentation.more.MoreViewModel
import ca.etsmtl.etsmobile.presentation.profile.ProfileFragment
import ca.etsmtl.etsmobile.presentation.profile.ProfileViewModel
import ca.etsmtl.etsmobile.presentation.student.StudentFragment
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

    @Binds
    @IntoMap
    @ViewModelKey(StudentViewModel::class)
    fun bindStudentViewModel(
        studentViewModel: StudentViewModel
    ): ViewModel

    @ContributesAndroidInjector
    fun contributeGradesFragment(): GradesFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesViewModel::class)
    fun bindGradesViewModel(
        gradesViewModel: GradesViewModel
    ): ViewModel

    @ContributesAndroidInjector
    fun contributeGradesDetailsFragment(): GradesDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesDetailsViewModel::class)
    fun bindGradesDetailsViewModel(
        gradesDetailsViewModel: GradesDetailsViewModel
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