package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.BaseActivity
import ca.etsmtl.etsmobile.util.show
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
            getShowLoading().observe(this@WelcomeActivity, Observer {
                progressBarWelcome.show(it == true)
            })

            getErrorMessage().observe(this@WelcomeActivity, Observer {
                it?.let { Toast.makeText(this@WelcomeActivity, it, Toast.LENGTH_LONG).show() }
            })

            getShowLoginFragment().observe(this@WelcomeActivity, Observer {
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

            getActivityToGoTo().observe(this@WelcomeActivity, Observer {
                with(Intent(this@WelcomeActivity, it)) {
                    startActivity(this)
                    finish()
                }
            })

            lifecycle.addObserver(this)
        }
    }
}
