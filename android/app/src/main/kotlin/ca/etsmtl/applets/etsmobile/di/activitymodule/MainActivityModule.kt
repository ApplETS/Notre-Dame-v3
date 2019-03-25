package ca.etsmtl.applets.etsmobile.di.activitymodule

import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.di.ViewModelKey
import ca.etsmtl.applets.etsmobile.presentation.dashboard.DashboardFragment
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades.GradesCardFragment
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades.GradesCardViewModel
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule.TodayScheduleCardFragment
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule.TodayScheduleCardViewModel
import ca.etsmtl.applets.etsmobile.presentation.ets.EtsFragment
import ca.etsmtl.applets.etsmobile.presentation.ets.EtsViewModel
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesFragment
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.applets.etsmobile.presentation.login.LoginFragment
import ca.etsmtl.applets.etsmobile.presentation.login.LoginViewModel
import ca.etsmtl.applets.etsmobile.presentation.more.MoreFragment
import ca.etsmtl.applets.etsmobile.presentation.more.MoreViewModel
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileFragment
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileViewModel
import ca.etsmtl.applets.etsmobile.presentation.schedule.ScheduleFragment
import ca.etsmtl.applets.etsmobile.presentation.schedule.ScheduleViewModel
import ca.etsmtl.applets.etsmobile.presentation.schedule.week.ScheduleWeekFragment
import ca.etsmtl.applets.etsmobile.presentation.splash.SplashFragment
import ca.etsmtl.applets.etsmobile.presentation.student.StudentFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import presentation.DashboardViewModel

/**
 * Created by Sonphil on 15-03-18.
 */
@Module
interface MainActivityModule {
    @ContributesAndroidInjector
    fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    /**
     * Cette fonction injecte, dans une Map, LoginViewModel::class en tant que clé. En effet, ceci
     * est clairement stipulé par les annotations(@IntoMap @ViewModelKey(LoginViewModel::class).
     * Cette clée est associée à un Provider qui aura le rôle d'instancier un LoginViewModel.
     */
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeDashboardFragment(): DashboardFragment

    @Binds
    @IntoMap
    @ViewModelKey(TodayScheduleCardViewModel::class)
    fun bindTodayScheduleViewModel(todayScheduleCardViewModel: TodayScheduleCardViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeTodayFragment(): TodayScheduleCardFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesCardViewModel::class)
    fun bindGradesCardViewModel(gradesCardViewModel: GradesCardViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeGradesCardFragment(): GradesCardFragment

    @ContributesAndroidInjector
    fun contributeStudentFragment(): StudentFragment

    @ContributesAndroidInjector
    fun contributeGradesFragment(): GradesFragment

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    fun bindScheduleViewModel(scheduleViewModel: ScheduleViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeScheduleFragment(): ScheduleFragment

    @ContributesAndroidInjector
    fun contributeScheduleWeekFragment(): ScheduleWeekFragment

    @Binds
    @IntoMap
    @ViewModelKey(GradesViewModel::class)
    fun bindGradesViewModel(gradesViewModel: GradesViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeProfileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(EtsViewModel::class)
    fun bindEtsViewModel(etsViewModel: EtsViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeEtsFragment(): EtsFragment

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
    fun bindMoreViewModel(moreViewModel: MoreViewModel): ViewModel
}