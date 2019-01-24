package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.toggle
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import javax.inject.Inject

/**
 * The fragment displaying the dashboard.
 *
 * Created by Sonphil on 26-06-18.
 */

class DashboardFragment : DaggerFragment() {

    private val dashboardViewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(activity as MainActivity) {
            appBarLayout.setExpanded(true, true)
            bottomNavigationView.toggle(true)
        }
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}
