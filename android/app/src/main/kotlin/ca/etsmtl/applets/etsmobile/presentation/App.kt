package ca.etsmtl.applets.etsmobile.presentation

import android.content.SharedPreferences
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.di.DaggerAppComponent
import ca.etsmtl.applets.etsmobile.extension.applyDarkThemePref
import ca.etsmtl.applets.repository.di.RepositoryModule
import com.buglife.sdk.Buglife
import com.buglife.sdk.InvocationMethod
import com.buglife.sdk.PickerInputField
import com.buglife.sdk.TextInputField
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class App : DaggerApplication() {

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        setupBuglife()
        loadDarkThemePref()
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

    /**
     * Load dark theme preference and apply it
     */
    private fun loadDarkThemePref() {
        val prefValue = prefs.getString(
                getString(R.string.key_dark_theme_pref),
                getString(R.string.default_entry_value_dark_theme_pref)
        )

        applyDarkThemePref(prefValue)
    }
}
