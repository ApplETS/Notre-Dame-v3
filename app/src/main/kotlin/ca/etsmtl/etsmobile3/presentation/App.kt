package ca.etsmtl.etsmobile3.presentation

import ca.etsmtl.etsmobile3.data.model.UserCredentials
import ca.etsmtl.etsmobile3.di.DaggerAppComponent
import ca.etsmtl.etsmobile3.di.DatabaseModule
import ca.etsmtl.etsmobile3.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by Sonphil on 28-02-18.
 */

class App : DaggerApplication() {

    companion object {
        @JvmStatic var userCredentials: UserCredentials? = null
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .networkModule(NetworkModule.instance)
                .databaseModule(DatabaseModule.instance)
                .build()
    }
}
