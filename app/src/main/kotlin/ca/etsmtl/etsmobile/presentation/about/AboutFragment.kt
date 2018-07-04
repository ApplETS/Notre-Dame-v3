package ca.etsmtl.etsmobile.presentation.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
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
        ViewCompat.setTransitionName(iv_applets_logo, transitionName)

        val transition = TransitionInflater
                .from(activity)
                .inflateTransition(R.transition.image_shared_element_transition)
        activity?.window?.sharedElementEnterTransition = transition

        activity?.window?.sharedElementReturnTransition = transition
    }
}
