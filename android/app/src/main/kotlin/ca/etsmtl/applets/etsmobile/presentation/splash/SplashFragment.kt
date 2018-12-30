package ca.etsmtl.applets.etsmobile.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.login.LoginViewModel
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.util.toast
import ca.etsmtl.applets.etsmobile.util.toggle
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.fragment_splash.iVBackground
import kotlinx.android.synthetic.main.fragment_splash.progressBarSplash
import javax.inject.Inject

/**
 * Created by Sonphil on 30-12-18.
 */

class SplashFragment : DaggerFragment() {
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(R.drawable.bg_ets_red).into(iVBackground)

        (activity as MainActivity).bottomNavigationView.toggle(false, 0)

        subscribeUI()
    }

    private fun subscribeUI() {
        with(loginViewModel) {
            showLoading.observe(this@SplashFragment, Observer {
                progressBarSplash.isVisible = it == true
            })

            errorMessage.observe(this@SplashFragment, EventObserver {
                context?.toast(it, Toast.LENGTH_LONG)
            })

            showLoginFragment.observe(this@SplashFragment, Observer {
                findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentLogin())
            })

            navigateToDashboard.observe(this@SplashFragment, EventObserver {
                findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentDashboard())
            })

            lifecycle.addObserver(this)
        }
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}