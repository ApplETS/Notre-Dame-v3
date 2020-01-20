package ca.etsmtl.applets.etsmobile.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import ca.etsmtl.applets.etsmobile.R
import dagger.android.support.DaggerAppCompatActivity
import model.UserCredentials
import java.util.Locale

/**
 * This base activity saves the user's credentials when [onSaveInstanceState] is called and restore
 * them when [onCreate] is called.
 *
 * Created by Sonphil on 20-05-18.
 */

const val STATE_SIGNETS_CREDENTIALS = "SignetsCredentials"
abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            val credentials: UserCredentials? = this.getParcelable(STATE_SIGNETS_CREDENTIALS)
            if (credentials != null) {
                UserCredentials.INSTANCE = credentials
            }
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_SIGNETS_CREDENTIALS, UserCredentials.INSTANCE)

        super.onSaveInstanceState(outState)
    }

    override fun attachBaseContext(newBase: Context) {
        val context = newBase.createLanguagePrefConfigurationContext()

        super.attachBaseContext(context)
    }

    @Suppress("DEPRECATION")
    private fun Context.createLanguagePrefConfigurationContext(): Context {
        val prefsFileName = getString(R.string.key_preference_file)
        val prefs = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        val langPrefKey = getString(R.string.key_language_pref)
        val lang = prefs.getString(langPrefKey, null) ?: getString(R.string.default_entry_value_language_pref)
        val locale = Locale(lang, "CA")
        Locale.setDefault(locale)
        val overrideConfiguration = resources
            .configuration
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setLocale(locale)
                } else {
                    this.locale = locale
                }
            }

        return createConfigurationContext(overrideConfiguration)
    }
}