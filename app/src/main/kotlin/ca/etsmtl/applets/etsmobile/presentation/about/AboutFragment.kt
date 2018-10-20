package ca.etsmtl.applets.etsmobile.presentation.about

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.openWithChromeCustomTabs
import ca.etsmtl.applets.etsmobile.util.show
import kotlinx.android.synthetic.main.fragment_about.backgroundAbout
import kotlinx.android.synthetic.main.fragment_about.btnFacebook
import kotlinx.android.synthetic.main.fragment_about.btnGithub
import kotlinx.android.synthetic.main.fragment_about.btnTwitter
import kotlinx.android.synthetic.main.fragment_about.btnYoutube
import kotlinx.android.synthetic.main.fragment_about.ivAppletsLogo

class AboutFragment : Fragment() {
    companion object {
        const val TAG = "AboutFragment"
        const val EXTRA_TRANSITION_NAME = "ExtraTransitionName"
        fun newInstance(transitionName: String?): AboutFragment {
            val fragment = AboutFragment()
            val bundle = Bundle().apply { putString(EXTRA_TRANSITION_NAME, transitionName) }
            fragment.arguments = bundle
            return fragment
        }
    }

    private val circularRevealRunnable = Runnable { executeCircularReveal() }
    private var isTransitionCanceled: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        activity?.supportStartPostponedEnterTransition()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getString(EXTRA_TRANSITION_NAME).isNullOrEmpty()) {
            view.post(circularRevealRunnable)
        } else {
            initViewTransition(savedInstanceState)
        }

        setSocialButtonsListener()
    }

    private fun initViewTransition(savedInstanceState: Bundle?) {
        val transitionName = arguments!!.getString(EXTRA_TRANSITION_NAME)

        ViewCompat.setTransitionName(ivAppletsLogo, transitionName)

        val transitionInflater = TransitionInflater.from(activity)

        if (savedInstanceState == null) {
            activity?.window?.sharedElementEnterTransition = transitionInflater
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
            backgroundAbout.show(true)
        }

        activity?.window?.sharedElementReturnTransition = transitionInflater
                .inflateTransition(R.transition.image_shared_element_transition)
    }

    private fun executeCircularReveal() {
        val revealView = backgroundAbout
        val centerX = ivAppletsLogo.run { (x + width / 2).toInt() }
        val centerY = ivAppletsLogo.run { (y + height / 2).toInt() }
        val endRadius = revealView.run { Math.max(revealView.width, revealView.height) }
        ViewAnimationUtils.createCircularReveal(revealView, centerX, centerY, 0f, endRadius.toFloat())
                .apply {
                    duration = 444
                    revealView.show(true)
                    start()
                }
    }

    override fun onDestroyView() {
        view?.removeCallbacks(circularRevealRunnable)

        super.onDestroyView()
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
                ).openWithChromeCustomTabs(it, R.color.bgApplets)
            }
        }) {
            btnGithub.setOnClickListener(this)
            btnFacebook.setOnClickListener(this)
            btnTwitter.setOnClickListener(this)
            btnYoutube.setOnClickListener(this)
        }
    }
}
