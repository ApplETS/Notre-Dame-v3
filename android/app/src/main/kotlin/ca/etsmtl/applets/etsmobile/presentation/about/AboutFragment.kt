package ca.etsmtl.applets.etsmobile.presentation.about

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.transition.addListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.getAndroidDimensionInPixelSize
import ca.etsmtl.applets.etsmobile.util.getColorCompat
import ca.etsmtl.applets.etsmobile.util.open
import ca.etsmtl.applets.etsmobile.util.toggle
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.fragment_about.backgroundAbout
import kotlinx.android.synthetic.main.fragment_about.btnFacebook
import kotlinx.android.synthetic.main.fragment_about.btnGithub
import kotlinx.android.synthetic.main.fragment_about.btnTwitter
import kotlinx.android.synthetic.main.fragment_about.btnWebsite
import kotlinx.android.synthetic.main.fragment_about.btnYoutube
import kotlinx.android.synthetic.main.fragment_about.content
import kotlinx.android.synthetic.main.fragment_about.ivAppletsLogo
import kotlinx.android.synthetic.main.fragment_about.toolbarAbout

class AboutFragment : Fragment() {

    private val circularRevealRunnable = Runnable {
        executeEnterCircularReveal()
    }
    private var isEnterTransitionCanceled: Boolean = false
    private val statusBarHeight by lazy {
        resources.getAndroidDimensionInPixelSize("status_bar_height") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compensateForTranslucentBar()
        setInitialActivityState()
        setupToolbar()
        initViewTransition(savedInstanceState)
        setSocialButtonsListener()
    }

    /**
     * Sets margin to compensate for the status bar height when setting
     * [WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS] to the window
     *
     * The flag allows the circular reveal animation to be integrated with the status bar.
     */
    private fun compensateForTranslucentBar() {
        (content.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            topMargin = statusBarHeight
        }
    }

    private fun initViewTransition(savedInstanceState: Bundle?) {
        val transitionInflater = TransitionInflater.from(activity)

        sharedElementReturnTransition = transitionInflater
            .inflateTransition(R.transition.image_shared_element_transition)

        if (savedInstanceState == null) {
            sharedElementEnterTransition = transitionInflater
                .inflateTransition(R.transition.image_shared_element_transition)
                .apply {
                    addListener(
                        onPause = { isEnterTransitionCanceled = true },
                        onCancel = { isEnterTransitionCanceled = true },
                        onEnd = {
                            if (!isEnterTransitionCanceled)
                                view?.post(circularRevealRunnable)
                        }
                    )
                }
        } else {
            backgroundAbout.isVisible = true
        }
    }

    private fun setupToolbar() {
        (activity as? MainActivity)?.let {
            it.appBarLayout.setExpanded(false, true)
            toolbarAbout.setupWithNavController(findNavController())
        }
    }

    private fun executeEnterCircularReveal() {
        val revealView = backgroundAbout
        val centerX = ivAppletsLogo.run { (x + width / 2).toInt() }
        val centerY = ivAppletsLogo.run {
            (y + statusBarHeight + toolbarAbout.height + height / 2).toInt()
        }
        val endRadius = revealView.run { Math.max(revealView.width, revealView.height) }
        ViewAnimationUtils.createCircularReveal(
            revealView,
            centerX,
            centerY,
            0f,
            endRadius.toFloat()
        ).apply {
            duration = 444
            revealView.isVisible = true
            start()
        }
    }

    override fun onDestroyView() {
        restoreActivityState()
        view?.removeCallbacks(circularRevealRunnable)

        super.onDestroyView()
    }

    private fun setInitialActivityState() {
        (activity as? MainActivity)?.let {
            it.appBarLayout.setExpanded(false, false)
            it.bottomNavigationView.toggle(false)
            it.window.apply {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )
                }
                statusBarColor = it.getColorCompat(R.color.applets)
                navigationBarColor = it.getColorCompat(R.color.applets)
            }
        }
    }

    private fun restoreActivityState() {
        (activity as? MainActivity)?.let {
            it.appBarLayout.setExpanded(true, true)
            it.bottomNavigationView.toggle(true)
            it.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                statusBarColor = it.getColorCompat(R.color.colorPrimaryDark)
                navigationBarColor = it.getColorCompat(R.color.colorPrimary)
            }
        }
    }

    private fun setSocialButtonsListener() {
        with(View.OnClickListener { view ->
            context?.let {
                Uri.parse(
                    getString(
                        when (view.id) {
                            R.id.btnWebsite -> R.string.uri_applets_website
                            R.id.btnGithub -> R.string.uri_applets_gh
                            R.id.btnFacebook -> R.string.uri_applets_fb
                            R.id.btnTwitter -> R.string.uri_applets_twitter
                            else -> R.string.uri_applets_yt
                        }
                    )
                ).open(it, R.color.applets)
            }
        }) {
            btnWebsite.setOnClickListener(this)
            btnGithub.setOnClickListener(this)
            btnFacebook.setOnClickListener(this)
            btnTwitter.setOnClickListener(this)
            btnYoutube.setOnClickListener(this)
        }
    }
}
