package ca.etsmtl.etsmobile.di.activitymodule

import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.di.ViewModelKey
import ca.etsmtl.etsmobile.presentation.login.LoginFragment
import ca.etsmtl.etsmobile.presentation.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
interface LoginActivityModule {

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    /**
     * Cette fonction injecte, dans une Map, LoginViewModel::class en tant que clé. En effet, ceci
     * est clairement stipulé par les annotations(@IntoMap @ViewModelKey(LoginViewModel::class).
     * Cette clée est associée à un Provider qui aura le rôle d'instancier un LoginViewModel.
     */
    @Binds
    @IntoMap @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(
        loginViewModel: LoginViewModel
    ): ViewModel
}