package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.applets

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.open
import ca.etsmtl.applets.etsmobile.extension.toast
import kotlinx.android.synthetic.main.fragment_applets_card.btnAppletsCardEmail
import kotlinx.android.synthetic.main.fragment_applets_card.btnAppletsCardFacebook
import kotlinx.android.synthetic.main.fragment_applets_card.btnAppletsCardGithub

/**
 * Created by Sonphil on 24-01-19.
 */

class AppletsCardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_applets_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        View.OnClickListener { btn ->
            when (btn.id) {
                R.id.btnAppletsCardFacebook -> navigateToWebsite(R.string.uri_applets_fb)
                R.id.btnAppletsCardGithub -> navigateToWebsite(R.string.uri_applets_gh)
                R.id.btnAppletsCardEmail -> openEmail()
            }
        }.apply {
            btnAppletsCardFacebook.setOnClickListener(this)
            btnAppletsCardGithub.setOnClickListener(this)
            btnAppletsCardEmail.setOnClickListener(this)
        }
    }

    private fun navigateToWebsite(@StringRes uri: Int) = context?.let { context ->
        Uri.parse(getString(uri)).open(context)
    }

    private fun openEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_applets)))
        }

        activity?.let { activity ->
            if (emailIntent.resolveActivity(activity.packageManager) != null) {
                startActivity(emailIntent)
            } else {
                activity.toast(R.string.error_no_email_app)
            }
        }
    }

    companion object {
        val TAG = "TodayScheduleCardFragment"
        fun newInstance() = AppletsCardFragment()
    }
}