package ca.etsmtl.applets.etsmobile.presentation.settings

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ca.etsmtl.applets.etsmobile.R

/**
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
        if (preference.key == getString(R.string.key_dark_theme_pref)) {
            val nightMode = when (newValue) {
                getString(R.string.entry_value_dark_theme_pref_enabled) -> {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                getString(R.string.entry_value_dark_theme_pref_disabled) -> {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                getString(R.string.entry_value_dark_theme_pref_battery_saver) -> {
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                }
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            AppCompatDelegate.setDefaultNightMode(nightMode)

            activity?.recreate()

            return true
        }

        return false
    }
}