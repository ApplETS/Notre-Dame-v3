package ca.etsmtl.applets.etsmobile.presentation.ets

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.open
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_ets.rvEts
import javax.inject.Inject

/**
 * This fragment shows information related to the university.
 *
 * Created by Sonphil on 28-06-18.
 */

class EtsFragment : DaggerFragment() {

    private val etsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(EtsViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_ets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeUI()
    }

    private fun setupRecyclerView() {
        rvEts.adapter = EtsRecyclerViewAdapter(etsViewModel.itemsList())
        rvEts.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW).apply {
            justifyContent = JustifyContent.SPACE_AROUND
        }
    }

    private fun subscribeUI() {
        etsViewModel.navigateToSecurity.observe(this, EventObserver {
            val nextAction = EtsFragmentDirections.actionNavigationEtsToSecurityFragment()
            Navigation.findNavController(view!!).navigate(nextAction)
        })

        etsViewModel.navigateToUri.observe(this, EventObserver { uriId ->
            Uri.parse(getString(uriId)).open(requireContext())
        })

        etsViewModel.navigateToUri.observe(this, EventObserver {
            context?.let { context ->
                Uri.parse(getString(R.string.uri_moodle)).open(context)
            }
        })
    }
}
