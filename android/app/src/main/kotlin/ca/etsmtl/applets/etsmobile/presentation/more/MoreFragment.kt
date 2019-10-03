package ca.etsmtl.applets.etsmobile.presentation.more

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.open
import ca.etsmtl.applets.etsmobile.extension.setVisible
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.fragment_more.bgAppletsItem
import kotlinx.android.synthetic.main.fragment_more.ivAppletsLogo
import kotlinx.android.synthetic.main.fragment_more.progressMore
import kotlinx.android.synthetic.main.fragment_more.recyclerViewMore
import kotlinx.android.synthetic.main.fragment_more.svMoreContent
import javax.inject.Inject

class MoreFragment : DaggerFragment() {

    private val moreViewModel: MoreViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MoreViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val reportBugDialog: AlertDialog? by lazy {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.AppTheme_Dialog_Alert)

            builder.setMessage(R.string.prompt_report_bug_method)
                .setTitle(getString(R.string.more_item_label_report_bug))
                .setPositiveButton(R.string.report_bug_method_screenshot) { _, _ ->
                    moreViewModel.reportBugWithScreenshot()
                }
                .setNegativeButton(R.string.report_bug_method_video) { _, _ ->
                    moreViewModel.reportBugWithVideo()
                }
                .setOnCancelListener { moreViewModel.clickLogoutConfirmationDialogButton(false) }

            builder.create()
        }
    }
    private val logoutConfirmationDialog: AlertDialog? by lazy {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.AppTheme_Dialog_Alert)

            builder.setMessage(R.string.prompt_log_out_confirmation)
                    .setTitle(getString(R.string.more_item_label_log_out))
                    .setPositiveButton(R.string.yes) { _, _ ->
                        moreViewModel.clickLogoutConfirmationDialogButton(true)
                    }
                    .setNegativeButton(R.string.no) { _, _ -> moreViewModel.clickLogoutConfirmationDialogButton(false) }
                    .setOnCancelListener { moreViewModel.clickLogoutConfirmationDialogButton(false) }

            builder.create()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_more, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        bgAppletsItem.setOnClickListener {
            moreViewModel.clickAbout()
        }

        subscribeUI()
    }

    private fun setupRecyclerView() {
        with(recyclerViewMore) {
            val itemsList = moreViewModel.itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList)
            setHasFixedSize(true)
        }
    }

    private fun goToAbout(iconView: View) {
        val extras = FragmentNavigatorExtras(
            iconView to getString(R.string.transition_about_applets_logo)
        )

        findNavController().navigate(MoreFragmentDirections.actionFragmentMoreToFragmentAbout(), extras)
    }

    private fun subscribeUI() {
        moreViewModel.displayLogoutConfirmationDialog.observe(this, Observer {
            logoutConfirmationDialog.takeIf { it != null && !it.isShowing }?.let { dialog ->
                if (it == true) {
                    dialog.show()
                } else {
                    dialog.dismiss()
                }
            }
        })

        moreViewModel.displayMessage.observe(this, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        moreViewModel.navigateToLogin.observe(this, EventObserver {
            with(activity as MainActivity) {
                appBarLayout.setExpanded(false, false)
                bottomNavigationView.setVisible(false)
                findNavController().navigate(MoreFragmentDirections.actionFragmentMoreToFragmentLogin())
            }
        })

        moreViewModel.navigateToAbout.observe(this, EventObserver {
            goToAbout(ivAppletsLogo)
        })

        moreViewModel.navigateToGitHubContributors.observe(this, EventObserver {
            findNavController().navigate(R.id.fragmentGitHubContributors)
        })

        moreViewModel.navigateToOpenSourcesLicenses.observe(this, EventObserver {
            findNavController().navigate(R.id.activityOpenSourceLicenses)
        })

        moreViewModel.navigateToSettings.observe(this, EventObserver {
            findNavController().navigate(MoreFragmentDirections.actionFragmentMoreToFragmentSettings())
        })

        moreViewModel.navigateToUri.observe(this, EventObserver { uriId ->
            Uri.parse(getString(uriId)).open(requireContext())
        })

        moreViewModel.loading.observe(this, Observer {
            it?.let { loading ->
                svMoreContent.isVisible = !loading
                progressMore.isVisible = loading
            }
        })

        moreViewModel.displayBugReportDialog.observe(this, Observer {
            reportBugDialog.takeIf { it != null && !it.isShowing }?.let { dialog ->
                if (it == true) {
                    dialog.show()
                } else {
                    dialog.dismiss()
                }
            }
        })
    }

    companion object {
        const val TAG = "MoreFragment"
        fun newInstance() = MoreFragment()
    }
}
