package ca.etsmtl.applets.etsmobile.presentation.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.repository.data.model.Etudiant
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.recyclerViewProfile
import kotlinx.android.synthetic.main.fragment_profile.swipeRefreshLayoutProfile
import javax.inject.Inject

/**
 * Displays the student's information (name, permanent code, balance, etc.)
 *
 * Created by Sonphil on 15-03-18.
 */

class ProfileFragment : DaggerFragment() {

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val adapter: ProfileAdapter by lazy {
        ProfileAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            setUpSwipeRefresh()
            setUpRecyclerView()
            subscribeUI()
        }
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutProfile.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutProfile.setOnRefreshListener { profileViewModel.refresh() }
    }

    private fun setUpRecyclerView() {
        recyclerViewProfile.adapter = adapter
        recyclerViewProfile.setHasFixedSize(true)
    }

    private fun subscribeUI() {
        profileViewModel.etudiant.observe(this, Observer<Etudiant> {
            it?.let { adapter.setEtudiant(it) }
        })
        profileViewModel.loading.observe(this, Observer<Boolean> {
            it?.let { swipeRefreshLayoutProfile.isRefreshing = it }
        })
        this.lifecycle.addObserver(profileViewModel)
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
