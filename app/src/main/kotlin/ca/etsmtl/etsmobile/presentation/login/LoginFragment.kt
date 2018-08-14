package ca.etsmtl.etsmobile.presentation.login

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.util.fadeTo
import ca.etsmtl.etsmobile.util.hideKeyboard
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.btnApplets
import kotlinx.android.synthetic.main.fragment_login.iVBackground
import kotlinx.android.synthetic.main.fragment_login.iVETSLogo
import kotlinx.android.synthetic.main.fragment_login.loginForm
import kotlinx.android.synthetic.main.fragment_login.progressLogin
import kotlinx.android.synthetic.main.fragment_login.tvMadeBy
import kotlinx.android.synthetic.main.layout_login_form.btnSignIn
import kotlinx.android.synthetic.main.layout_login_form.btnUniversalCodeInfo
import kotlinx.android.synthetic.main.layout_login_form.layoutPassword
import kotlinx.android.synthetic.main.layout_login_form.layoutUniversalCode
import kotlinx.android.synthetic.main.layout_login_form.password
import kotlinx.android.synthetic.main.layout_login_form.universalCode
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImages()

        setUpFields()

        View.OnClickListener {
            when {
                it.id == R.id.btnSignIn -> { clearFocusAndSubmitCredentials() }
                it.id == R.id.btnUniversalCodeInfo -> displayUniversalCodeDialog()
                it.id == R.id.btnApplets -> loginViewModel.clickOnAppletsLogo()
            }
        }.apply {
            btnSignIn.setOnClickListener(this)
            btnUniversalCodeInfo.setOnClickListener(this)
            btnApplets.setOnClickListener(this)
        }

        subscribeUI()
    }

    private fun loadImages() {
        Glide.with(this).load(R.drawable.bg_ets_red).into(iVBackground)
        Glide.with(this).load(R.drawable.ets_blanc_impr_fond_transparent).into(iVETSLogo)
    }

    private fun setUpFields() {
        val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                when (view.id) {
                    R.id.universalCode -> {
                        loginViewModel.setUniversalCode(universalCode.text.toString())
                    }
                    R.id.password -> {
                        loginViewModel.setPassword(password.text.toString())
                    }
                }
            }
        }

        universalCode.onFocusChangeListener = onFocusChangeListener
        password.onFocusChangeListener = onFocusChangeListener

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                loginViewModel.setUniversalCode(universalCode.text.toString())
                loginViewModel.setPassword(password.text.toString())
                clearFocusAndSubmitCredentials()
                return@OnEditorActionListener true
            }
            false
        })

        // Set the password layout font
        activity?.let {
            val fontValue = TypedValue()
            it.theme.resolveAttribute(R.attr.fontFamily, fontValue, true)
            val passwordLayoutTypeFace = ResourcesCompat.getFont(it, fontValue.resourceId)
            layoutPassword.setTypeface(passwordLayoutTypeFace)
        }
    }

    /**
     * Subscribes the UI to [loginViewModel]'s LiveData's and add [loginViewModel] as an observer
     * of the lifecycle
     */
    private fun subscribeUI() {
        with (loginViewModel) {
            getShowLoading().observe(this@LoginFragment, Observer {
                showProgress(it == true)
            })

            getErrorMessage().observe(this@LoginFragment, Observer {
                it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
            })

            getUniversalCodeError().observe(this@LoginFragment, Observer {
                setFieldError(layoutUniversalCode, it)
            })

            getPasswordError().observe(this@LoginFragment, Observer {
                setFieldError(layoutPassword, it)
            })

            getActivityToGoTo().observe(this@LoginFragment, Observer {
                with(Intent(context, it)) {
                    startActivity(this)
                }
            })

            getHideKeyboard().observe(this@LoginFragment, Observer {
                activity?.currentFocus?.hideKeyboard()
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

    private fun displayUniversalCodeDialog() {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.AppCompatAlertDialogStyle)

            val icon = ContextCompat.getDrawable(it, R.drawable.ic_info_white_24dp)!!
                    .mutate()
            icon.setTint(ContextCompat.getColor(it, R.color.colorPrimary))

            builder.setMessage(R.string.info_universal_code)
                    .setTitle(getString(R.string.prompt_universal_code))
                    .setIcon(icon)
                    .setPositiveButton(android.R.string.ok) { dialog, which -> dialog?.dismiss() }

            builder.create().show()
        }
    }

    /**
     * Clears the focus on each field and submit the credentials
     *
     * Clearing the focus will set the universal code and password in [loginViewModel] and trigger
     * a validity check for each field
     */
    private fun clearFocusAndSubmitCredentials() {
        universalCode.clearFocus()
        password.clearFocus()
        loginViewModel.submitCredentials()
    }

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }
}