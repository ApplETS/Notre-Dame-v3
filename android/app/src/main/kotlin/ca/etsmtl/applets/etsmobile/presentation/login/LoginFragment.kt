package ca.etsmtl.applets.etsmobile.presentation.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.extension.fadeTo
import ca.etsmtl.applets.etsmobile.extension.getColorCompat
import ca.etsmtl.applets.etsmobile.extension.hideKeyboard
import ca.etsmtl.applets.etsmobile.extension.open
import ca.etsmtl.applets.etsmobile.extension.setVisible
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.btnApplets
import kotlinx.android.synthetic.main.fragment_login.iVETSLogo
import kotlinx.android.synthetic.main.fragment_login.loginForm
import kotlinx.android.synthetic.main.fragment_login.progressLogin
import kotlinx.android.synthetic.main.fragment_login.tvMadeBy
import kotlinx.android.synthetic.main.include_login_form.btnForgotPassword
import kotlinx.android.synthetic.main.include_login_form.btnSignIn
import kotlinx.android.synthetic.main.include_login_form.btnUniversalCodeInfo
import kotlinx.android.synthetic.main.include_login_form.layoutPassword
import kotlinx.android.synthetic.main.include_login_form.layoutUniversalCode
import kotlinx.android.synthetic.main.include_login_form.password
import kotlinx.android.synthetic.main.include_login_form.universalCode
import model.UniversalCode
import javax.inject.Inject

/**
 * A login fragment that offers login via universal code/password.
 *
 * Created by Sonphil on 10-05-18.
 */

class LoginFragment : DaggerFragment() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val universalCodeInfoDialog by lazy {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.AppCompatAlertDialogStyle)

            val icon = it.getDrawable(R.drawable.ic_info_outline_white_24dp)?.mutate()
            icon?.setTint(it.getColorCompat(R.color.colorPrimary))

            builder.setMessage(R.string.info_universal_code)
                    .setTitle(getString(R.string.prompt_universal_code))
                    .setIcon(icon)
                    .setPositiveButton(android.R.string.ok) { _, _ -> loginViewModel.displayUniversalCodeInfo(false) }
                    .setOnCancelListener { loginViewModel.displayUniversalCodeInfo(false) }

            builder.create()
        }
    }
    /**
     * A focus listener used to submit the universal code or password to [LoginViewModel] when the
     * focus is lost. When a value is submitted, [LoginViewModel] will perform a validity check.
     */
    private val credentialsFieldsOnFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            view.setField()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).bottomNavigationView.setVisible(false, 0)

        Glide.with(this).load(R.drawable.ets_blanc_impr_fond_transparent).into(iVETSLogo)

        setupFields()

        View.OnClickListener {
            when (it.id) {
                R.id.btnSignIn -> { toggleFocusAndSubmitCredentials() }
                R.id.btnUniversalCodeInfo -> loginViewModel.displayUniversalCodeInfo(true)
                R.id.btnForgotPassword -> {
                    context?.let {
                        Uri.parse(getString(R.string.uri_password_forgotten)).open(it)
                    }
                }
            }
        }.apply {
            btnSignIn.setOnClickListener(this)
            btnUniversalCodeInfo.setOnClickListener(this)
            btnForgotPassword.setOnClickListener(this)
            btnApplets.setOnClickListener(this)
        }

        subscribeUI()
    }

    private fun setupFields() {
        universalCode.onFocusChangeListener = credentialsFieldsOnFocusChangeListener
        password.onFocusChangeListener = credentialsFieldsOnFocusChangeListener

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                loginViewModel.setUniversalCode(UniversalCode(universalCode.text.toString()))
                loginViewModel.setPassword(password.text.toString())
                toggleFocusAndSubmitCredentials()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun View.setField() = when (id) {
        R.id.universalCode -> loginViewModel.setUniversalCode(UniversalCode(universalCode.text.toString()))
        R.id.password -> loginViewModel.setPassword(password.text.toString())
        else -> Unit
    }

    /**
     * Subscribes the UI to [loginViewModel]'s LiveData's and add [loginViewModel] as an observer
     * of the lifecycle
     */
    private fun subscribeUI() {
        with(loginViewModel) {
            showLoading.observe(this@LoginFragment, Observer {
                showProgress(it == true)
            })

            errorMessage.observe(this@LoginFragment, EventObserver {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })

            universalCodeError.observe(this@LoginFragment, Observer {
                setFieldError(layoutUniversalCode, it)
            })

            passwordError.observe(this@LoginFragment, Observer {
                setFieldError(layoutPassword, it)
            })

            navigateToDashboard.observe(this@LoginFragment, EventObserver {
                with((activity as MainActivity)) {
                    findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentDashboard())
                }
            })

            hideKeyboard.observe(this@LoginFragment, Observer {
                btnSignIn.hideKeyboard()
            })

            displayUniversalCodeDialog.observe(this@LoginFragment, Observer {
                if (it == true) {
                    universalCodeInfoDialog?.show()
                } else {
                    universalCodeInfoDialog?.dismiss()
                }
            })

            lifecycle.addObserver(this)
        }
    }

    private fun setFieldError(textInputLayout: TextInputLayout, errorMessage: String?) {
        if (errorMessage.isNullOrEmpty()) {
            textInputLayout.error = null
        } else {
            textInputLayout.error = errorMessage
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private fun showProgress(show: Boolean) {
        if (show) {
            progressLogin.fadeTo(View.VISIBLE)
            loginForm.visibility = View.INVISIBLE
            iVETSLogo.visibility = View.INVISIBLE
            tvMadeBy.visibility = View.INVISIBLE
            btnApplets.visibility = View.INVISIBLE
        } else {
            progressLogin.fadeTo(View.GONE)
            loginForm.visibility = View.VISIBLE
            iVETSLogo.visibility = View.VISIBLE
            tvMadeBy.visibility = View.VISIBLE
            btnApplets.visibility = View.VISIBLE
        }
    }

    /**
     * Toggles the focus on each field and submit the credentials
     *
     * Toggling the focus will set the universal code and password in [loginViewModel] and trigger
     * a validity check for each field
     */
    private fun toggleFocusAndSubmitCredentials() {
        universalCode.requestFocus()
        universalCode.clearFocus()
        password.requestFocus()
        password.clearFocus()
        loginViewModel.submitCredentials()
    }

    override fun onDestroyView() {
        /*
        The focus will be lost. However, we don't want to submit the credentials, so we must remove
        the focus change listener to prevent it from submitting the credentials.
         */
        universalCode.onFocusChangeListener = null
        password.onFocusChangeListener = null

        super.onDestroyView()
    }

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }
}