package ca.etsmtl.applets.etsmobile.presentation.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.applyAppTheme
import ca.etsmtl.applets.etsmobile.extension.toLiveData
import ca.etsmtl.applets.etsmobile.extension.toast
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_github_contributors.rvGitHubContributors
import kotlinx.android.synthetic.main.fragment_github_contributors.swipeRefreshLayoutGitHubContributors
import presentation.GitHubContributorsViewModel
import javax.inject.Inject

/**
 * Created by Sonphil on 02-10-19.
 */

class GitHubContributorsFragment : DaggerFragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GitHubContributorsViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter = GitHubContributorsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_github_contributors, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefreshLayout()
        subscribeUI()
        viewModel.fetchContributors()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayoutGitHubContributors.applyAppTheme(requireContext())
        swipeRefreshLayoutGitHubContributors.setOnRefreshListener {
            viewModel.refreshContributors()
        }
    }

    private fun setupRecyclerView() {
        rvGitHubContributors.adapter = adapter
    }

    private fun subscribeUI() {
        viewModel.contributors
            .openSubscription()
            .toLiveData()
            .observe(this, Observer { contributors ->
                if (contributors != null) {
                    adapter.items = contributors
                }
            })

        viewModel.showLoading
            .openSubscription()
            .toLiveData()
            .observe(this, Observer { showLoading ->
                swipeRefreshLayoutGitHubContributors.isRefreshing = showLoading
            })

        viewModel.errorMessage
            .openSubscription()
            .toLiveData()
            .observe(this, Observer { message ->
                context?.toast(message)
            })
    }
}