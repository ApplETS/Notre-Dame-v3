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
import ca.etsmtl.applets.etsmobile.extension.toLiveData
import ca.etsmtl.applets.etsmobile.extension.toast
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_splash.progressBarSplash
import presentation.SplashViewModel
import javax.inject.Inject

/**
 * Created by Sonphil on 30-12-18.
 */

class SplashFragment : DaggerFragment() {
    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
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

        subscribeUI()
        splashViewModel.submitSavedCredentials()
    }

    private fun subscribeUI() {
        with(splashViewModel) {
            showLoading.toLiveData().observe(this@SplashFragment, Observer {
                progressBarSplash.isVisible = it == true
            })

            errorMessage.observe(this@SplashFragment, EventObserver {
                if (!it.equals(context?.getString(R.string.error_no_internet_connection)))
                    context?.toast(it, Toast.LENGTH_LONG)
            })

            navigateToLogin.toLiveData().observe(this@SplashFragment, Observer {
                findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentLogin())
            })

            navigateToDashboard.toLiveData().observe(this@SplashFragment, Observer {
                findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentDashboard())
            })
        }
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}