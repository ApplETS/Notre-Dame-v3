package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.os.Build
import ca.etsmtl.applets.etsmobile.R
import java.util.Locale

/**
 * Created by Sonphil on 20-01-20.
 */

object LocaleUtils {
    fun updateContext(context: Context): Context {
        val lang = context.getLanguagePref()
        val locale = Locale(lang, "CA")
        Locale.setDefault(locale)
        val overrideConfiguration = context
            .resources
            .configuration
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setLocale(locale)
                } else {
                    this.locale = locale
                }
            }

        return context.createConfigurationContext(overrideConfiguration)
    }

    private fun Context.getLanguagePref(): String {
        val prefsFileName = getString(R.string.key_preference_file)
        val prefs = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        val langPrefKey = getString(R.string.key_language_pref)

        return prefs.getString(langPrefKey, null) ?: getString(R.string.default_entry_value_language_pref)
    }
}