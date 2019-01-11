package ca.etsmtl.applets.etsmobile.presentation

import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.di.DaggerAppComponent
import ca.etsmtl.applets.repository.di.RepositoryModule
import com.buglife.sdk.Buglife
import com.buglife.sdk.InvocationMethod
import com.buglife.sdk.PickerInputField
import com.buglife.sdk.TextInputField
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by Sonphil on 28-02-18.
 */

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        setupBuglife()
    }

    private fun setupBuglife() {
        Buglife.initWithApiKey(this, getString(R.string.buglife_key))
        Buglife.setInvocationMethod(InvocationMethod.BUG_BUTTON)
        Buglife.setInvocationMethod(InvocationMethod.SHAKE)

        val summaryField = TextInputField.summaryInputField()
        val emailField = TextInputField(getString(R.string.buglife_email))
        emailField.setMultiline(false)
        val reproducibilityField = PickerInputField(getString(R.string.buglife_reproducibility))
        resources.getStringArray(R.array.buglife_reproducibility_levels).forEach {
            reproducibilityField.addOption(it)
        }
        val stepsField = TextInputField(getString(R.string.buglife_steps_to_reproduce))
        stepsField.setMultiline(true)

        Buglife.setInputFields(summaryField, emailField, reproducibilityField, stepsField)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .repositoryModule(RepositoryModule(this))
                .build()
    }
}
