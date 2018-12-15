package ca.etsmtl.applets.etsmobile.presentation.about

import android.animation.Animator
import android.net.Uri
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.getColorCompat
import ca.etsmtl.applets.etsmobile.util.isVisible
import ca.etsmtl.applets.etsmobile.util.open
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.fragment_about.backgroundAbout
import kotlinx.android.synthetic.main.fragment_about.btnFacebook
import kotlinx.android.synthetic.main.fragment_about.btnGithub
import kotlinx.android.synthetic.main.fragment_about.btnTwitter
import kotlinx.android.synthetic.main.fragment_about.btnYoutube
import kotlinx.android.synthetic.main.fragment_about.ivAppletsLogo

class AboutFragment : Fragment() {
    companion object {
        const val TAG = "AboutFragment"
    }

    private val circularRevealRunnable = Runnable {
        executeCircularReveal()
        setActivityState()
    }
    private var isTransitionCanceled: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementReturnTransition = TransitionInflater.from(activity)
            .inflateTransition(R.transition.image_shared_element_transition)

        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        initViewTransition(savedInstanceState)

        setSocialButtonsListener()
    }

    private fun initViewTransition(savedInstanceState: Bundle?) {
        val transitionInflater = TransitionInflater.from(activity)

        startPostponedEnterTransition()

        if (savedInstanceState == null) {
            sharedElementEnterTransition = transitionInflater
                    .inflateTransition(R.transition.image_shared_element_transition)
                    .addListener(object : Transition.TransitionListener {
                        override fun onTransitionResume(transition: Transition) {}

                        override fun onTransitionPause(transition: Transition) {
                            isTransitionCanceled = true
                        }

                        override fun onTransitionCancel(transition: Transition) {
                            isTransitionCanceled = true
                        }

                        override fun onTransitionStart(transition: Transition) {}

                        override fun onTransitionEnd(transition: Transition) {
                            if (!isTransitionCanceled)
                                view?.post(circularRevealRunnable)
                        }
                    })
        } else {
            backgroundAbout.isVisible = true
            setActivityState()
        }
    }

    private fun executeCircularReveal() {
        val revealView = backgroundAbout
        val centerX = ivAppletsLogo.run { (x + width / 2).toInt() }
        val centerY = ivAppletsLogo.run { (y + height / 2).toInt() }
        val endRadius = revealView.run { Math.max(revealView.width, revealView.height) }
        ViewAnimationUtils.createCircularReveal(revealView, centerX, centerY, 0f, endRadius.toFloat())
                .apply {
                    duration = 444
                    revealView.isVisible = true
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animator: Animator) {}

                        override fun onAnimationEnd(animator: Animator) {}

                        override fun onAnimationCancel(animator: Animator) {}

                        override fun onAnimationStart(animator: Animator) {}
                    })
                    start()
                }
    }

    override fun onDestroyView() {
        restoreActivityState()
        view?.removeCallbacks(circularRevealRunnable)

        super.onDestroyView()
    }

    private fun setActivityState() {
        (activity as? MainActivity)?.let {
            it.toolbar.setBackgroundColor(it.getColorCompat(R.color.bgApplets))
            it.toggleBottomNavigationView(false)
            it.window.statusBarColor = it.getColorCompat(R.color.bgApplets)
        }
    }

    private fun restoreActivityState() {
        (activity as? MainActivity)?.let {
            it.toolbar.setBackgroundColor(it.getColorCompat(R.color.colorPrimary))
            it.toggleBottomNavigationView(true)
            it.window.statusBarColor = it.getColorCompat(R.color.colorPrimaryDark)
        }
    }

    private fun setSocialButtonsListener() {
        with(View.OnClickListener { view ->
            context?.let {
                Uri.parse(
                        getString(
                                when (view.id) {
                                    R.id.btnGithub -> R.string.uri_applets_gh
                                    R.id.btnFacebook -> R.string.uri_applets_fb
                                    R.id.btnTwitter -> R.string.uri_applets_twitter
                                    else -> R.string.uri_applets_yt
                                }
                        )
                ).open(it, R.color.bgApplets)
            }
        }) {
            btnGithub.setOnClickListener(this)
            btnFacebook.setOnClickListener(this)
            btnTwitter.setOnClickListener(this)
            btnYoutube.setOnClickListener(this)
        }
    }
}
