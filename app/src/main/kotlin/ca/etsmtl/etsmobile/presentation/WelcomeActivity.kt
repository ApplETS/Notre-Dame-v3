package ca.etsmtl.etsmobile.presentation

import android.os.Bundle
import ca.etsmtl.etsmobile.R

/**
 * First activity displayed to the user
 */
class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        setTitle(R.string.title_activity_login)
    }
}
