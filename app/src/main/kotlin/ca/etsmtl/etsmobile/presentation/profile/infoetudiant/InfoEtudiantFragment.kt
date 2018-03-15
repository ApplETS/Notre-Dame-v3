package ca.etsmtl.etsmobile.presentation.profile.infoetudiant


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R


/**
 * Created by Sonphil on 15-03-18.
 */

class InfoEtudiantFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_etudiant, container, false)
    }

    companion object {
        fun newInstance(): InfoEtudiantFragment {
            val fragment = InfoEtudiantFragment()
            return fragment
        }
    }
}
