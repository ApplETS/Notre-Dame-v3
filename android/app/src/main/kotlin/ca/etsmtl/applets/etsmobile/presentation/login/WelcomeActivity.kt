package ca.etsmtl.applets.etsmobile.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.util.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_welcome.iVBackground
import kotlinx.android.synthetic.main.activity_welcome.progressBarWelcome
import javax.inject.Inject

/**
 * First activity displayed to the user
 */
class WelcomeActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        // Setting theme to LoginTheme because, on launch, the theme is set to SplashTheme in order
        // to display the splash screen
        setTheme(R.style.LoginTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        setTitle(R.string.title_activity_login)

        Glide.with(this).load(R.drawable.bg_ets_red).into(iVBackground)

        subscribeUI()
    }

    private fun subscribeUI() {
        with(loginViewModel) {
            showLoading.observe(this@WelcomeActivity, Observer {
                progressBarWelcome.isVisible = it == true
            })

            errorMessage.observe(this@WelcomeActivity, EventObserver {
                Toast.makeText(this@WelcomeActivity, it, Toast.LENGTH_LONG).show()
            })

            showLoginFragment.observe(this@WelcomeActivity, Observer {
                with(supportFragmentManager.beginTransaction()) {
                    replace(
                            R.id.layoutRootWelcome,
                            supportFragmentManager.findFragmentByTag(LoginFragment.TAG)
                                    ?: LoginFragment.newInstance(),
                            LoginFragment.TAG
                    )
                    commit()
                }
            })

            activityToGoTo.observe(this@WelcomeActivity, Observer {
                with(Intent(this@WelcomeActivity, it)) {
                    startActivity(this)
                    finish()
                }
            })

            lifecycle.addObserver(this)
        }
    }
}
