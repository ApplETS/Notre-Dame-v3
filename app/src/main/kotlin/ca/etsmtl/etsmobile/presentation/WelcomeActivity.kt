package ca.etsmtl.etsmobile.presentation

import android.os.Bundle
import ca.etsmtl.etsmobile.R

/**
 * First activity displayed to the user
 */
class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Setting theme to LoginTheme because, on launch, the theme is set to SplashTheme in order
        // to display the splash screen
        setTheme(R.style.LoginTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        setTitle(R.string.title_activity_login)
    }
}
