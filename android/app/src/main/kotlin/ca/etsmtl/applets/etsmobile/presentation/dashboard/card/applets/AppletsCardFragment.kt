package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.applets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.dashboard.DashboardFragment

/**
 * Created by Sonphil on 24-01-19.
 */

class AppletsCardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_applets_card, container, false)

    companion object {
        val TAG = "AppletsCardFragment"
        fun newInstance() = DashboardFragment()
    }
}