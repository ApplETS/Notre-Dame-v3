package ca.etsmtl.etsmobile.presentation.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import kotlinx.android.synthetic.main.fragment_about.*

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
    }

    private fun initViewTransition(savedInstanceState: Bundle?) {
        val transitionName = arguments!!.getString(EXTRA_TRANSITION_NAME)

        ViewCompat.setTransitionName(iv_applets_logo, transitionName)

        val transitionInflater = TransitionInflater.from(activity)

        if (savedInstanceState == null) {
            activity?.window?.sharedElementEnterTransition = transitionInflater
                    .inflateTransition(R.transition.image_shared_element_transition)
                    .addListener(object : Transition.TransitionListener {
                        override fun onTransitionResume(transition: Transition) {}

                        override fun onTransitionPause(transition: Transition) {}

                        override fun onTransitionCancel(transition: Transition) {}

                        override fun onTransitionStart(transition: Transition) {}

                        override fun onTransitionEnd(transition: Transition) {
                            view?.post(circularRevealRunnable)
                        }
                    })
        } else {
            background_about.visibility = View.VISIBLE
        }

        activity?.window?.sharedElementReturnTransition = transitionInflater
                .inflateTransition(R.transition.image_shared_element_transition)
    }

    private fun executeCircularReveal() {
        val revealView = background_about
        val centerX = iv_applets_logo.run { (x + width / 2).toInt() }
        val centerY = iv_applets_logo.run { (y + height / 2).toInt() }
        ViewAnimationUtils.createCircularReveal(revealView, centerX, centerY, 0f, revealView.width.toFloat())
                .apply {
                    duration = 444
                    revealView.visibility = View.VISIBLE
                    start()
                }
    }

    override fun onDestroyView() {
        view?.removeCallbacks(circularRevealRunnable)

        super.onDestroyView()
    }
}
