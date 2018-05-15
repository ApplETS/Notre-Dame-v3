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
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.presentation.MainActivity
import ca.etsmtl.etsmobile.util.KeyboardUtils
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.bg_iv
import kotlinx.android.synthetic.main.fragment_login.login_form
import kotlinx.android.synthetic.main.fragment_login.progress_login
import kotlinx.android.synthetic.main.layout_login_form.password
import kotlinx.android.synthetic.main.layout_login_form.password_layout
import kotlinx.android.synthetic.main.layout_login_form.sign_in_button
import kotlinx.android.synthetic.main.layout_login_form.universal_code
import kotlinx.android.synthetic.main.layout_login_form.universal_code_info_btn
import kotlinx.android.synthetic.main.layout_login_form.universal_code_layout
import javax.inject.Inject

/**
 * A login fragment that offers login via universal code/password.
 *
 * Created by Sonphil on 10-05-18.
 */

class LoginFragment : DaggerFragment() {
    companion object {
        const val TAG = "LogiNFragment"
        fun newInstance() = LoginFragment()
    }

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
        Glide.with(this).load(R.drawable.bg_ets_red).into(bg_iv)

        val onClickListener = View.OnClickListener {
            when {
                it.id == R.id.sign_in_button -> attemptLogin()
                it.id == R.id.universal_code_info_btn -> displayUniversalCodeDialog()
            }
        }

        sign_in_button.setOnClickListener(onClickListener)
        universal_code_info_btn.setOnClickListener(onClickListener)

        subscribeUI()

        initLoginFormWithSavedCredentials()
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
        activity?.let {
            val fontValue = TypedValue()
            it.theme.resolveAttribute(R.attr.fontFamily, fontValue, true)
            val passwordLayoutTypeFace = ResourcesCompat.getFont(it, fontValue.resourceId)
            password_layout.setTypeface(passwordLayoutTypeFace)
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
        activity?.currentFocus?.let { KeyboardUtils.hideKeyboard(it) }

        val universalCodeStr = universal_code.text.toString()
        val passwordStr = password.text.toString()

        loginViewModel.setUserCredentials(SignetsUserCredentials(universalCodeStr, passwordStr))
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.INVISIBLE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.INVISIBLE else View.VISIBLE
                    }
                })

        progress_login.visibility = if (show) View.VISIBLE else View.GONE
        progress_login.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        progress_login.visibility = if (show) View.VISIBLE else View.GONE
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