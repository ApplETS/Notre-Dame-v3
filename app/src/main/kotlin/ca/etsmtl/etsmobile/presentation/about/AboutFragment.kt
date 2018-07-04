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
import kotlinx.android.synthetic.main.fragment_about.background_about
import kotlinx.android.synthetic.main.fragment_about.iv_applets_logo

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.supportStartPostponedEnterTransition()

        if (arguments?.getString(EXTRA_TRANSITION_NAME)?.isNotEmpty() == true) {
            initViewTransition()
        }
    }

    private fun initViewTransition() {
        val transitionName = arguments!!.getString(EXTRA_TRANSITION_NAME)

        background_about.visibility = View.INVISIBLE

        ViewCompat.setTransitionName(iv_applets_logo, transitionName)

        val transition = TransitionInflater
                .from(activity)
                .inflateTransition(R.transition.image_shared_element_transition)

        activity?.window?.sharedElementReturnTransition = transition

        activity?.window?.sharedElementEnterTransition = transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(transition: Transition) {}

            override fun onTransitionPause(transition: Transition) {}

            override fun onTransitionCancel(transition: Transition) {}

            override fun onTransitionStart(transition: Transition) {}

            override fun onTransitionEnd(transition: Transition) {
                executeCircularReveal()
            }
        })

        activity?.window?.sharedElementReturnTransition = transition
    }

    private fun executeCircularReveal() {
        val revealView = background_about
        val cx = iv_applets_logo.run { (x + width / 2).toInt() }
        val cy = iv_applets_logo.run { (y + height / 2).toInt() }
        ViewAnimationUtils.createCircularReveal(revealView, cx, cy, 0f, revealView.width.toFloat())
                .apply {
                    duration = 400
                    revealView.visibility = View.VISIBLE
                    start()
                }
    }
}
