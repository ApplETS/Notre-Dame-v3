package ca.etsmtl.etsmobile.presentation.ets

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * This fragment shows information related to the university.
 *
 * Created by Sonphil on 28-06-18.
 */

class EtsFragment : Fragment() {

    companion object {
        private const val ETS_FRAGMENT_TAG = "EtsFragment"
        fun newInstance() = EtsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setTitle(R.string.title_ets)
    }
}
