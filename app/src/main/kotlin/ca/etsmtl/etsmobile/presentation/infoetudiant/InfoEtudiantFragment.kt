package ca.etsmtl.etsmobile.presentation.infoetudiant

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.presentation.App
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_info_etudiant.*
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

        subscribeUI()
    }

    companion object {
        fun newInstance(): InfoEtudiantFragment {
            val fragment = InfoEtudiantFragment()
            return fragment
        }
    }

    private fun subscribeUI() {
        val userCredentials = App.userCredentials.get()

        if (userCredentials != null) {
            infoEtudiantViewModel.getInfoEtudiant(userCredentials).observe(this, Observer<Resource<Etudiant>> { res ->
                if (res != null) {
                    when (res.status) {
                        Resource.SUCCESS -> {
                            info_etudiant_progress_bar.visibility = View.GONE
                            text_view.text = res.data.toString()
                        }
                        Resource.ERROR -> {
                            info_etudiant_progress_bar.visibility = View.GONE
                            text_view.text = res.message + res.data.toString()
                        }
                        Resource.LOADING -> {
                            info_etudiant_progress_bar.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }
}
