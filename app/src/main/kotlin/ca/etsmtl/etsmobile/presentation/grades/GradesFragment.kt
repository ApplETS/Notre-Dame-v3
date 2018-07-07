package ca.etsmtl.etsmobile.presentation.grades

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R

/**
 * This fragment shows the grades of the user.
 *
 * Created by Sonphil on 24-02-18.
 */

class GradesFragment : Fragment() {

    companion object {
        private const val GRADES_FRAGMENT_TAG = "GradesFragment"
        fun newInstance() = GradesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grades, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
