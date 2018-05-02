package ca.etsmtl.etsmobile.presentation.infoetudiant

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_info_etudiant.progress_bar_info_etudiant
import kotlinx.android.synthetic.main.fragment_info_etudiant.recycler_view_info_etudiant
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */

class InfoEtudiantFragment : DaggerFragment() {

    private val infoEtudiantViewModel: InfoEtudiantViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(InfoEtudiantViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var adapter: InfoEtudiantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_etudiant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycleView()
        subscribeUI()
    }

    private fun setUpRecycleView() {
        adapter = InfoEtudiantAdapter()
        recycler_view_info_etudiant.adapter = adapter
        recycler_view_info_etudiant.setHasFixedSize(true)
    }

    companion object {
        fun newInstance() = InfoEtudiantFragment()
    }

    private fun subscribeUI() {
        infoEtudiantViewModel.getInfoEtudiant().observe(this, Observer<Resource<Etudiant>> { res ->
            when (res?.status) {
                Resource.SUCCESS -> {
                    progress_bar_info_etudiant.visibility = View.GONE
                    res.data?.let { adapter.setEtudiant(it) }
                }
                Resource.ERROR -> {
                    progress_bar_info_etudiant.visibility = View.GONE
                    res.data?.let { adapter.setEtudiant(it) }
                }
                Resource.LOADING -> {
                    progress_bar_info_etudiant.visibility = View.VISIBLE
                }
            }
        })
    }
}
