package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.toLiveData
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardAdapter
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardsTouchHelperCallback
import ca.etsmtl.applets.etsmobile.util.Const
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_dashboard.rvCards
import presentation.DashboardViewModel
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
    private var undoActionView: View? = null
    private val undoSnackBar: Snackbar by lazy {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            R.string.msg_dashboard_card_removed,
            Snackbar.LENGTH_LONG
        ).setAction(R.string.cancel) { view ->
            undoActionView = view
            view.isEnabled = false
            dashboardViewModel.undoLastRemove()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If a restart occurred from the settings screen, navigate back to it.
        if (arguments?.getBoolean(Const.ARG_SETTINGS_RESTART, false) == true) {
            findNavController().navigate(R.id.fragmentMore)
            findNavController().navigate(R.id.fragmentSettings)
        } else {
            setupRecyclerView()

            subscribeUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.item_restore -> {
            dashboardViewModel.restore()
            true
        }
        else -> super.onContextItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = DashboardCardAdapter(childFragmentManager)

        rvCards.adapter = adapter
        ItemTouchHelper(DashboardCardsTouchHelperCallback(
            dashboardViewModel,
            childFragmentManager
        )).attachToRecyclerView(rvCards)
    }

    private fun subscribeUI() {
        dashboardViewModel
            .cardsChannel
            .toLiveData()
            .observe(this@DashboardFragment, Observer {
                adapter.items = it
            })

        dashboardViewModel
            .showUndoRemoveChannel
            .toLiveData()
            .observe(this, Observer { show ->
                undoActionView?.isEnabled = show

                if (show) {
                    undoSnackBar.show()
                } else {
                    undoSnackBar.dismiss()
                }
            })

        dashboardViewModel.load()
    }

    override fun onPause() {
        dashboardViewModel.save()

        super.onPause()
    }

    override fun onDestroyView() {
        rvCards.adapter = null

        super.onDestroyView()
    }
}
