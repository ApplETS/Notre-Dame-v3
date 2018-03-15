package ca.etsmtl.etsmobile.presentation

import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.di.DaggerAppComponent
import ca.etsmtl.etsmobile.di.DatabaseModule
import ca.etsmtl.etsmobile.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by Sonphil on 28-02-18.
 */

class App : DaggerApplication() {

    companion object {
        @JvmStatic var userCredentials: AtomicReference<UserCredentials> = AtomicReference()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .networkModule(NetworkModule.instance)
                .databaseModule(DatabaseModule.instance)
                .build()
    }
}
