package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.toggle
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (activity as MainActivity) {
            appBarLayout.setExpanded(true, true)
            bottomNavigationView.toggle(true)
        }
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}
