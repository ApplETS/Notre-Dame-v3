package ca.etsmtl.applets.etsmobile.presentation.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.synthetic.main.fragment_security.*

/**
 * This fragment contains information about the security.
 */
class SecurityFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val itemsList = resources.getStringArray(R.array.security_type)
        security_recycler_view.layoutManager=LinearLayoutManager(context)
        security_recycler_view.adapter = SecurityAdapter(itemsList,findNavController())
        security_recycler_view.setHasFixedSize(true)


    }
}
