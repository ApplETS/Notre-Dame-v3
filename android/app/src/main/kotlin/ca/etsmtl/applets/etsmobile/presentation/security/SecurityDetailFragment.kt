package ca.etsmtl.applets.etsmobile.presentation.security

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.toggle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_security_detail.*

class SecurityDetailFragment : Fragment() {
    private val args: SecurityDetailFragmentArgs by navArgs()

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
        hideBottomNavigation()
        setEmergencyDetailText()
        setButtonListener()
    }

    private fun setEmergencyDetailText() {
        val securityTypeList = resources.getStringArray(R.array.security_type)
        webView.loadUrl(
            when (args.securityTitle) {
                securityTypeList[0] -> resources.getString(R.string.bomb_threat)
                securityTypeList[1] -> resources.getString(R.string.suspicious_packages)
                securityTypeList[2] -> resources.getString(R.string.evacuation)
                securityTypeList[3] -> resources.getString(R.string.gas_leak)
                securityTypeList[4] -> resources.getString(R.string.fire)
                securityTypeList[5] -> resources.getString(R.string.broken_elevator)
                securityTypeList[6] -> resources.getString(R.string.electrical_outage)
                securityTypeList[7] -> resources.getString(R.string.armed_person)
                securityTypeList[8] -> resources.getString(R.string.earthquake)
                else -> resources.getString(R.string.medical_emergency)
            }
        )
    }

    private fun setButtonListener() {
        urgence_appel_urgence.setOnClickListener {
            val uri = "tel:" + resources.getString(R.string.emergency_number)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        restoreBottomNavigation()
        super.onDestroyView()
    }

    private fun hideBottomNavigation() {
        (activity as? MainActivity)?.bottomNavigationView?.toggle(false)
    }

    private fun restoreBottomNavigation() {
        (activity as? MainActivity)?.bottomNavigationView?.toggle(true)
    }
}