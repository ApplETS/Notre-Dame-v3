package ca.etsmtl.applets.etsmobile.extension

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 11-05-19.
 */

fun Context.applyDarkThemePref(modePreferenceValue: String?) {
    val nightMode = when (modePreferenceValue) {
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
}