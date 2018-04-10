package ca.etsmtl.etsmobile.presentation.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.presentation.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * A login screen that offers login via universal code/password.
 */
class LoginActivity : DaggerAppCompatActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        title = getString(R.string.title_activity_login)
        sign_in_button.setOnClickListener {
            hideKeyboard()
            attemptLogin()
        }

        subscribeUI()

        with(loginViewModel.getSavedUserCredentials()) {
            initUserCredentialsFields(this)

            // Attempt to login if user credentials are not null
            this?.let { attemptLogin() }
        }
    }

    /**
     * Subscribe the UI to the user credentials LiveData
     */
    private fun subscribeUI() {
        loginViewModel.getUserCredentialsIsValid().observe(this, Observer<Resource<Boolean>> { resource ->
            if (resource != null) {
                when (resource.status) {
                    Resource.SUCCESS -> {
                        showProgress(false)
                        displayMainActivity()
                    }
                    Resource.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.LOADING -> {
                        showProgress(true)
                    }
                }
            }
        })
    }

    /**
     * Fill the credentials fields with the specifies user credentials
     *
     * @param userCredentials the user credentials used to fill the fields
     */
    private fun initUserCredentialsFields(userCredentials: UserCredentials?) {
        if (userCredentials != null) {
            universal_code.setText(userCredentials.codeAccesUniversel)
            password.setText(userCredentials.motPasse)
        }
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid universal code, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        // Reset errors.
        universal_code_layout.error = null
        password_layout.error = null

        // Store values at the time of the login attempt.
        val universalCodeStr = universal_code.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check if the user entered the motPasse
        if (TextUtils.isEmpty(passwordStr)) {
            password_layout.error = getString(R.string.error_field_required)
            focusView = password
            cancel = true
        }

        // Check for a valid universal code.
        if (TextUtils.isEmpty(universalCodeStr)) {
            universal_code_layout.error = getString(R.string.error_field_required)
            focusView = universal_code
            cancel = true
        } else if (!isUniversalCodeValid(universalCodeStr)) {
            universal_code_layout.error = getString(R.string.error_invalid_universal_code)
            focusView = universal_code
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            loginViewModel.setUserCredentials(UserCredentials(universalCodeStr, passwordStr))
        }
    }

    /**
     * Returns whether a given universal code is valid or not
     *
     * @param universalCode the universal code to check
     */
    private fun isUniversalCodeValid(universalCode: String): Boolean {
        return universalCode.matches(Regex("[a-zA-Z]{2}\\d{5}"))
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    /**
     * Starts MainActivity
     */
    private fun displayMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}
