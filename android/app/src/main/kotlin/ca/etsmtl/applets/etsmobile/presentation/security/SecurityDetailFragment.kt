package ca.etsmtl.applets.etsmobile.presentation.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.getColorCompat
import ca.etsmtl.applets.etsmobile.util.toggle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_security_detail.*

class SecurityDetailFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.bottomNavigationView?.toggle(false)

        val safeArgs = SecurityDetailFragmentArgs.fromBundle(arguments).securityName
        (activity as? MainActivity)?.toolbar!!.title=safeArgs

        val itemsList = resources.getStringArray(R.array.security_type)
        var url: String = ""

        when (safeArgs) {
            itemsList[0] -> url = resources.getString(R.string.bomb_threat)
            itemsList[1] -> url = resources.getString(R.string.suspicious_packages)
            itemsList[2] -> url = resources.getString(R.string.evacuation)
            itemsList[3] -> url = resources.getString(R.string.gas_leak)
            itemsList[4] -> url = resources.getString(R.string.fire)
            itemsList[5] -> url = resources.getString(R.string.broken_elevator)
            itemsList[6] -> url = resources.getString(R.string.electrical_outage)
            itemsList[7] -> url = resources.getString(R.string.armed_person)
            itemsList[8] -> url = resources.getString(R.string.earthquake)
            itemsList[9] -> url = resources.getString(R.string.medical_emergency)
        }
        webView.loadUrl(url)
        webView.requestFocus()
    }

    override fun onDestroyView() {
        restoreActivityState()
        super.onDestroyView()
    }

    private fun restoreActivityState() {
        (activity as? MainActivity)?.let {
            it.appBarLayout.setExpanded(true, true)
            it.bottomNavigationView.toggle(true)
            it.window.statusBarColor = it.getColorCompat(R.color.colorPrimaryDark)
        }
    }
}