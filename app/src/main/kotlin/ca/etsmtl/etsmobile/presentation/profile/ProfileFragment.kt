package ca.etsmtl.etsmobile.presentation.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Etudiant
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.progressBarInfoEtudiant
import kotlinx.android.synthetic.main.fragment_profile.recyclerViewInfoEtudiant
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
    lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        subscribeUI()
    }

    private fun setUpRecyclerView() {
        adapter = ProfileAdapter()
        recyclerViewInfoEtudiant.adapter = adapter
        recyclerViewInfoEtudiant.setHasFixedSize(true)
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private fun subscribeUI() {
        profileViewModel.getInfoEtudiant().observe(this, Observer<Resource<Etudiant>> { res ->
            when (res?.status) {
                Resource.SUCCESS -> {
                    progressBarInfoEtudiant.visibility = View.GONE
                    res.data?.let { adapter.setEtudiant(it) }
                }
                Resource.ERROR -> {
                    progressBarInfoEtudiant.visibility = View.GONE
                    res.data?.let { adapter.setEtudiant(it) }
                }
                Resource.LOADING -> {
                    progressBarInfoEtudiant.visibility = View.VISIBLE
                }
            }
        })
    }
}
