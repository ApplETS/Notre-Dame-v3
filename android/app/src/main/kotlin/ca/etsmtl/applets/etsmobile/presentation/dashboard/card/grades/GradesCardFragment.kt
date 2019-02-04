package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 04-02-19.
 */

class GradesCardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_grades_card, container, false)

    companion object {
        fun newInstance() = GradesCardFragment()
    }
}