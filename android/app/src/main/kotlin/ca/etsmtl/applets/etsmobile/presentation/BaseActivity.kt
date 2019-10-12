package ca.etsmtl.applets.etsmobile.presentation

import android.os.Bundle
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
}