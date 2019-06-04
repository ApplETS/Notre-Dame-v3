package ca.etsmtl.applets.etsmobile.presentation.security

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.coordinatorLayout
import kotlinx.android.synthetic.main.btn_emergency_call.btnEmergencyCall
import kotlinx.android.synthetic.main.btn_emergency_call.view.btnEmergencyCall
import kotlinx.android.synthetic.main.fragment_security_detail.webView

class SecurityDetailFragment : Fragment() {
    private val args: SecurityDetailFragmentArgs by navArgs()
    private var emergencyCallBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity)?.let { activity ->
            activity.appBarLayout?.setExpanded(true, true)
            addEmergencyCallButtonToMainLayout(activity, inflater)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security_detail, container, false)
    }

    /**
     * Inflates and adds the emergency call button to the main layout
     *
     * The emergency call FloatingActionButton should be positioned at the bottom of this
     * [Fragment]'s layout. However, the [MainActivity]'s app bar will push the [Fragment]'s layout
     * down and cause the button to be partially hidden. To prevent this from happening, we add the
     * button to the [MainActivity]'s CoordinatorLayout and we remove afterwards when the user
     * leaves this [Fragment].
     *
     * @param activity [MainActivity]
     * @param inflater [LayoutInflater] instance used to instantiates the button
     */
    private fun addEmergencyCallButtonToMainLayout(
        activity: MainActivity,
        inflater: LayoutInflater
    ) {
        activity.coordinatorLayout?.let { coordinatorLayout ->
            emergencyCallBtn = coordinatorLayout.btnEmergencyCall

            if (emergencyCallBtn == null) {
                emergencyCallBtn = inflater.inflate(
                    R.layout.btn_emergency_call,
                    coordinatorLayout,
                    false
                ) as Button

                coordinatorLayout.addView(emergencyCallBtn)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEmergencyDetailText()
        setButtonListener()
    }

    private fun setEmergencyDetailText() {
        val securityTypeList = resources.getStringArray(R.array.array_security_type)
        val fileUrlIndex = securityTypeList.indexOf(args.securityTitle)
        val fileUrl = resources.getStringArray(R.array.array_security_file)[fileUrlIndex]

        webView.loadUrl(fileUrl)
    }

    private fun setButtonListener() = (activity as? MainActivity)
        ?.btnEmergencyCall
        ?.setOnClickListener {
            val uri = "tel:" + resources.getString(R.string.emergency_number)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

    override fun onDestroyView() {
        (activity as? MainActivity)?.let {
            it.appBarLayout?.setExpanded(true, true)

            // Remove emergency call button from MainActivity
            emergencyCallBtn?.setOnClickListener(null)
            (emergencyCallBtn?.parent as? ViewGroup)?.removeView(emergencyCallBtn)
        }

        super.onDestroyView()
    }
}