package ca.etsmtl.etsmobile.presentation

import android.os.Bundle
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import dagger.android.support.DaggerAppCompatActivity

/**
 * This base activity saves the user's credentials when [onSaveInstanceState] is called and restore
 * them when [onCreate] is called.
 *
 * Created by Sonphil on 20-05-18.
 */

abstract class BaseActivity : DaggerAppCompatActivity() {
    companion object {
        const val STATE_SIGNETS_CREDENTIALS = "SignetsCredentials"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            val signetsCredentials: SignetsUserCredentials? = this.getParcelable(STATE_SIGNETS_CREDENTIALS)
            if (signetsCredentials != null) {
                SignetsUserCredentials.INSTANCE.set(signetsCredentials)
            }
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_SIGNETS_CREDENTIALS, SignetsUserCredentials.INSTANCE.get())

        super.onSaveInstanceState(outState)
    }
}