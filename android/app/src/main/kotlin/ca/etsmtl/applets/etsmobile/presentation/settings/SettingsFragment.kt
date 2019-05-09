package ca.etsmtl.applets.etsmobile.presentation.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity

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

        val darkThemePref: Preference = findPreference(getString(R.string.key_dark_theme_pref))

        darkThemePref.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return if (preference.key == getString(R.string.key_dark_theme_pref)) {
            handleDarkThemePreferenceChange(newValue)

            true
        } else {
            false
        }
    }

    private fun handleDarkThemePreferenceChange(newValue: Any?) {
        if (activity is MainActivity && newValue is String) {
            (activity as MainActivity).applyDarkThemePref(newValue)

            activity?.recreate()
        }
    }
}