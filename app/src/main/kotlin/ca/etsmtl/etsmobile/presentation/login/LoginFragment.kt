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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repos.data.model.Resource
import ca.etsmtl.repos.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.presentation.MainActivity
import ca.etsmtl.etsmobile.util.KeyboardUtils
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.iVBackground
import kotlinx.android.synthetic.main.fragment_login.loginForm
import kotlinx.android.synthetic.main.fragment_login.progressLogin
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

        setUpFields()

        // Set up background
        Glide.with(this).load(R.drawable.bg_ets_red).into(iVBackground)

        val onClickListener = View.OnClickListener {
            when {
                it.id == R.id.btnSignIn -> attemptLogin()
                it.id == R.id.btnUniversalCodeInfo -> displayUniversalCodeDialog()
            }
        }

        btnSignIn.setOnClickListener(onClickListener)
        btnUniversalCodeInfo.setOnClickListener(onClickListener)

        subscribeUI()

        initWithSavedCredentials()
    }

    /**
     * Gets the saved credentials from the view model. If are the credentials are not null, the form
     * will be filled up and an login attempt will be made
     */
    private fun initWithSavedCredentials() {
        loginViewModel.getSavedUserCredentials()?.let {
            fillLoginForm(it)
            attemptLogin()
        }
    }

    /**
     * Fills the credentials fields with the specified user credentials
     *
     * @param userCredentials The user credentials to fill the fields with
     */
    private fun fillLoginForm(userCredentials: SignetsUserCredentials) {
        universalCode.setText(userCredentials.codeAccesUniversel)
        password.setText(userCredentials.motPasse)
    }

    private fun setUpFields() {
        val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val fieldStatus: FieldStatus
                when (view.id) {
                    R.id.universalCode -> {
                        fieldStatus = loginViewModel.setUniversalCode(universalCode.text.toString())
                        adjustTextInputAccordingToStatus(layoutUniversalCode, fieldStatus)
                    }
                    R.id.password -> {
                        fieldStatus = loginViewModel.setPassword(password.text.toString())
                        adjustTextInputAccordingToStatus(layoutPassword, fieldStatus)
                    }
                }
            }
        }

        universalCode.onFocusChangeListener = onFocusChangeListener
        password.onFocusChangeListener = onFocusChangeListener

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
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
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
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
     * Attempts to sign in the account
     */
    private fun attemptLogin() {
        activity?.currentFocus?.let { KeyboardUtils.hideKeyboard(it) }

        val universalCodeStr = universalCode.text.toString()
        val passwordStr = password.text.toString()

        loginViewModel.setUserCredentials(SignetsUserCredentials(universalCodeStr, passwordStr))
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        loginForm.visibility = if (show) View.INVISIBLE else View.VISIBLE
        loginForm.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        loginForm.visibility = if (show) View.INVISIBLE else View.VISIBLE
                    }
                })

        progressLogin.visibility = if (show) View.VISIBLE else View.GONE
        progressLogin.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        progressLogin.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    private fun displayUniversalCodeDialog() {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.AppCompatAlertDialogStyle)

            val icon = ContextCompat.getDrawable(it, R.drawable.ic_info_white_24dp)!!
                    .mutate()
            icon.setTint(ContextCompat.getColor(it, R.color.colorPrimary))

            builder.setMessage(R.string.infos_universal_code)
                    .setTitle(getString(R.string.prompt_universal_code))
                    .setIcon(icon)
                    .setPositiveButton(android.R.string.ok) { dialog, which -> dialog?.dismiss() }

            builder.create().show()
        }
    }

    /**
     * Starts MainActivity
     */
    private fun goToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}