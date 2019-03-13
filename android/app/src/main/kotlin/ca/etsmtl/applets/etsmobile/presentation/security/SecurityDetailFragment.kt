package ca.etsmtl.applets.etsmobile.presentation.security

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.fragment_security_detail.appBarLayoutSecurity
import kotlinx.android.synthetic.main.fragment_security_detail.btnEmergencyCall
import kotlinx.android.synthetic.main.fragment_security_detail.toolbarSecurity
import kotlinx.android.synthetic.main.fragment_security_detail.webView

class SecurityDetailFragment : Fragment() {
    private val args: SecurityDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity)?.appBarLayout?.setExpanded(false, false)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarSecurityDetail()
        setEmergencyDetailText()
        setButtonListener()
    }

    private fun setEmergencyDetailText() {
        val securityTypeList = resources.getStringArray(R.array.array_security_type)
        val fileUrlIndex = securityTypeList.indexOf(args.securityTitle)
        val fileUrl = resources.getStringArray(R.array.array_security_file)[fileUrlIndex]

        webView.loadUrl(fileUrl)
    }

    private fun setButtonListener() {
        btnEmergencyCall.setOnClickListener {
            val uri = "tel:" + resources.getString(R.string.emergency_number)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

    private fun setupToolbarSecurityDetail() {
        toolbarSecurity.setupWithNavController(findNavController())
        appBarLayoutSecurity?.setExpanded(true, true)
    }

    override fun onDestroyView() {
        (activity as? MainActivity)?.appBarLayout?.setExpanded(true, false)

        super.onDestroyView()
    }
}