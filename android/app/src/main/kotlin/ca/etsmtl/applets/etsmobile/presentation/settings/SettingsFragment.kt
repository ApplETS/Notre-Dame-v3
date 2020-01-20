package ca.etsmtl.applets.etsmobile.presentation.settings

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavDeepLinkBuilder
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.applyDarkThemePref
import ca.etsmtl.applets.etsmobile.util.Const
import com.buglife.sdk.Buglife
import com.buglife.sdk.InvocationMethod

/**
 * Settings screen
 *
 * Created by Sonphil on 08-05-19.
 */

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = getString(R.string.key_preference_file)
        preferenceManager.sharedPreferencesMode = Context.MODE_PRIVATE
        addPreferencesFromResource(R.xml.preferences)

        listOf(
            R.string.key_dark_theme_pref,
            R.string.key_shake_bug_reporter_invocation_method_pref,
            R.string.key_language_pref
        ).forEach { keyId ->
            val pref = findPreference<Preference>(getString(keyId))

            pref?.onPreferenceChangeListener = this
        }
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return when (preference.key) {
            getString(R.string.key_dark_theme_pref) -> {
                handleDarkThemePreferenceChange(newValue)

                true
            }
            getString(R.string.key_shake_bug_reporter_invocation_method_pref) -> {
                handleShakeBugReporterInvocationMethodPreferenceChange(newValue)

                true
            }
            getString(R.string.key_language_pref) -> {
                handleLanguagePreferenceChange()

                true
            }
            else -> false
        }
    }

    private fun handleDarkThemePreferenceChange(newValue: Any?) {
        if (newValue is String) {
            context?.applyDarkThemePref(newValue)
        }
    }

    private fun handleShakeBugReporterInvocationMethodPreferenceChange(newValue: Any?) {
        Buglife.setInvocationMethod(
            if (newValue == true) {
                InvocationMethod.SHAKE
            } else {
                InvocationMethod.NONE
            }
        )
    }

    /**
     * Restarts the application to apply language change
     */
    private fun handleLanguagePreferenceChange() = restartApp()

    private fun restartApp() {
        val args = Bundle().apply {
            this.putBoolean(Const.ARG_SETTINGS_RESTART, true)
        }
        val pendingIntent = NavDeepLinkBuilder(requireContext())
            .setGraph(R.navigation.nav_graph_main)
            .setDestination(R.id.fragmentDashboard)
            .setArguments(args)
            .createPendingIntent()

        pendingIntent.send()
    }
}