package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardAdapter
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardsTouchHelperCallback
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.toggle
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.fragment_dashboard.rvCards
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
    private lateinit var adapter: DashboardCardAdapter

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

        setupRecyclerView()

        subscribeUI()
    }

    private fun setupRecyclerView() {
        adapter = DashboardCardAdapter(childFragmentManager, dashboardViewModel)

        rvCards.adapter = adapter
        ItemTouchHelper(DashboardCardsTouchHelperCallback(adapter)).attachToRecyclerView(rvCards)
    }

    private fun subscribeUI() {
        dashboardViewModel.cards.observe(this, Observer {
            adapter.items = it.toMutableList()
        })

        dashboardViewModel.showUndoCardRemove.observe(this, Observer {
            activity?.let { activity ->
                Snackbar.make(
                    activity.findViewById(android.R.id.content),
                    R.string.msg_dashboard_card_removed,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.cancel) {
                    dashboardViewModel.undoLastRemove()
                }.show()
            }
        })
    }
}
