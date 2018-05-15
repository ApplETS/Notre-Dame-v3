package ca.etsmtl.etsmobile.presentation.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.infoetudiant.InfoEtudiantFragment
import kotlinx.android.synthetic.main.fragment_profile.expansionLayoutInfoEtudiant

/**
 * Created by Sonphil on 24-02-18.
 */

class ProfileFragment : Fragment() {

    companion object {
        private const val INFO_ETUDIANT_FRAGMENT_TAG = "InfoEtudiantFragment"
        fun newInstance() = ProfileFragment()
    }

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

        expansionLayoutInfoEtudiant.addListener { _, expanded ->
            if (expanded)
                addInfoEtudiantFragment()
        }

        if (expansionLayoutInfoEtudiant.isExpanded)
            addInfoEtudiantFragment()
    }

    private fun addInfoEtudiantFragment() {
        var infoEtudiantFragment = childFragmentManager.findFragmentByTag(INFO_ETUDIANT_FRAGMENT_TAG)

        if (infoEtudiantFragment == null)
            infoEtudiantFragment = InfoEtudiantFragment.newInstance()

        with(childFragmentManager.beginTransaction()) {
            replace(R.id.containerInfoEtudiant, infoEtudiantFragment, INFO_ETUDIANT_FRAGMENT_TAG)
            commit()
        }
    }
}
