package ca.etsmtl.etsmobile.presentation

import android.os.Bundle
import ca.etsmtl.etsmobile.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * First activity displayed to the user
 */
class WelcomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        setTitle(R.string.title_activity_login)
    }
}
