package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import ca.etsmtl.applets.etsmobile.R
import java.util.Locale

/**
 * Created by Sonphil on 20-01-20.
 */

object LocaleUtils {
    fun createConfiguration(
        context: Context,
        language: String = context.getLanguagePref()
    ): Configuration {
        val locale = Locale(language, "CA")
        Locale.setDefault(locale)

        return context
            .resources
            .configuration
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setLocale(locale)
                } else {
                    this.locale = locale
                }
            }
    }

    private fun Context.getLanguagePref(): String {
        val prefsFileName = getString(R.string.key_preference_file)
        val prefs = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        val langPrefKey = getString(R.string.key_language_pref)

        return prefs.getString(langPrefKey, null) ?: getString(R.string.default_entry_value_language_pref)
    }
}