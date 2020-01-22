package ca.etsmtl.applets.etsmobile.presentation

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import ca.etsmtl.applets.etsmobile.util.LocaleUtils
import dagger.android.support.DaggerAppCompatActivity
import model.UserCredentials

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
        val overrideConfiguration = LocaleUtils.createConfiguration(newBase)
        val updatedBase = newBase.createConfigurationContext(overrideConfiguration)

        super.attachBaseContext(updatedBase)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        /*
        Even though we have set the configuration correctly in [attachBaseContext],
        the [AppCompatDelegateImpl] will override the configuration to a new configuration without
        a Locale. As result, the app switches to the default language when the user enables or
        disables dark theme. The code below fixes this issue by setting the correct configuration
        and setting the correct [uiMode] (the one determined by [AppCompatDelegateImpl]).
        */
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode

            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }

        super.applyOverrideConfiguration(overrideConfiguration)
    }
}