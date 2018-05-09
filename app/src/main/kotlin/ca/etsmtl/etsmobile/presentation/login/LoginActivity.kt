package ca.etsmtl.etsmobile.presentation.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.presentation.MainActivity
import ca.etsmtl.etsmobile.presentation.login.LoginActivity.Companion.LOGGING_OUT_EXTRA
import ca.etsmtl.etsmobile.util.KeyboardUtils
import com.bumptech.glide.Glide
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.bg_iv
import kotlinx.android.synthetic.main.activity_login.login_form
import kotlinx.android.synthetic.main.activity_login.login_progress
import kotlinx.android.synthetic.main.layout_login_form.password
import kotlinx.android.synthetic.main.layout_login_form.password_layout
import kotlinx.android.synthetic.main.layout_login_form.sign_in_button
import kotlinx.android.synthetic.main.layout_login_form.universal_code
import kotlinx.android.synthetic.main.layout_login_form.universal_code_layout
import javax.inject.Inject

/**
 * A login screen that offers login via universal code/password.
 * This activity can also offers logout if [LOGGING_OUT_EXTRA] extra is set to true
 */
class LoginActivity : DaggerAppCompatActivity() {

    companion object {
        const val LOGGING_OUT_EXTRA: String = "LoggingOut"
    }

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setTitle(R.string.title_activity_login)

        setUpFields()

        // Set up background
        Glide.with(this).load(R.drawable.bg_ets_red).into(bg_iv)

        sign_in_button.setOnClickListener {
            attemptLogin()
        }

        subscribeUI()

        if (intent.getBooleanExtra(LOGGING_OUT_EXTRA, false)) {
            logOut()
        } else {
            initLoginFormWithSavedCredentials()
        }
    }

    private fun logOut() {
        showProgress(true)
        loginViewModel.logOut().observe(this, Observer<Boolean> { finished ->
            if (finished != null && finished) {
                Toast.makeText(this@LoginActivity, R.string.msg_logout_success,
                        Toast.LENGTH_LONG).show()

                showProgress(false)
            }
        })
    }

    private fun initLoginFormWithSavedCredentials() {
        with(loginViewModel.getSavedUserCredentials()) {
            initUserCredentialsFields(this)

            // Attempt to login if user credentials are not null
            this?.let { attemptLogin() }
        }
    }

    private fun setUpFields() {
        val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val fieldStatus: FieldStatus
                when (view.id) {
                    R.id.universal_code -> {
                        fieldStatus = loginViewModel.setUniversalCode(universal_code.text.toString())
                        adjustTextInputAccordingToStatus(universal_code_layout, fieldStatus)
                    }
                    R.id.password -> {
                        fieldStatus = loginViewModel.setPassword(password.text.toString())
                        adjustTextInputAccordingToStatus(password_layout, fieldStatus)
                    }
                }
            }
        }

        universal_code.onFocusChangeListener = onFocusChangeListener
        password.onFocusChangeListener = onFocusChangeListener

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        // Set the password layout font
        val fontValue = TypedValue()
        theme.resolveAttribute(R.attr.fontFamily, fontValue, true)
        val passwordLayoutTypeFace = ResourcesCompat.getFont(this, fontValue.resourceId)
        password_layout.setTypeface(passwordLayoutTypeFace)
    }

    /**
     * Subscribes the UI to the LiveData
     */
    private fun subscribeUI() {
        loginViewModel.userCredentialsValidLD.observe(this, Observer<Resource<Boolean>> { resource ->
            if (resource != null) {
                when (resource.status) {
                    Resource.SUCCESS -> {
                        goToMainActivity()
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

    private fun adjustTextInputAccordingToStatus(textInputLayout: TextInputLayout, fieldStatus: FieldStatus?) {
        fieldStatus?.let {
            if (fieldStatus.valid) {
                textInputLayout.error = null
            } else {
                textInputLayout.error = fieldStatus.error
            }
        }
    }

    /**
     * Fills the credentials fields with the specifies user credentials
     *
     * @param userCredentials the user credentials used to fill the fields
     */
    private fun initUserCredentialsFields(userCredentials: SignetsUserCredentials?) {
        if (userCredentials != null) {
            universal_code.setText(userCredentials.codeAccesUniversel)
            password.setText(userCredentials.motPasse)
        }
    }

    /**
     * Attempts to sign in the account
     */
    private fun attemptLogin() {
        currentFocus?.let { KeyboardUtils.hideKeyboard(currentFocus) }

        val universalCodeStr = universal_code.text.toString()
        val passwordStr = password.text.toString()

        loginViewModel.setUserCredentials(SignetsUserCredentials(universalCodeStr, passwordStr))
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

    fun displayUniversalCodeDialog(view: View) {
        val builder = AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)

        val icon = ContextCompat.getDrawable(this, R.drawable.ic_info_white_24dp)!!
                .mutate()
        icon.setTint(ContextCompat.getColor(this, R.color.colorPrimary))

        builder.setMessage(R.string.infos_universal_code)
                .setTitle(getString(R.string.prompt_universal_code))
                .setIcon(icon)
                .setPositiveButton(android.R.string.ok) { dialog, which -> dialog?.dismiss() }

        builder.create().show()
    }

    /**
     * Starts MainActivity
     */
    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
