package ca.etsmtl.applets.etsmobile.presentation.ets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R

/**
 * This fragment shows information related to the university.
 *
 * Created by Sonphil on 28-06-18.
 */

class EtsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_ets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

    }

    companion object {
        fun newInstance() = EtsFragment()
    }
}
