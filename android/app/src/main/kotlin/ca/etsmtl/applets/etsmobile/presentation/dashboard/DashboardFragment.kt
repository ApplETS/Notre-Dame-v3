package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R

/**
 * The fragment displaying the dashboard.
 *
 * Created by Sonphil on 26-06-18.
 */

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    companion object {
        fun newInstance() = DashboardFragment()
    }
}
