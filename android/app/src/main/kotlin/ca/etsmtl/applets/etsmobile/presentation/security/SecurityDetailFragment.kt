package ca.etsmtl.applets.etsmobile.presentation.security

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R
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

        val safeArgs = SecurityDetailFragmentArgs.fromBundle(arguments).securityName
        val itemsList = resources.getStringArray(R.array.security_type)
//        toolbar.title = safeArgs
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE;
        val htmlAsString: String
        val htmlAsSpanned: Spanned
        when (safeArgs) {
            itemsList[0] -> {
                htmlAsString = getString(R.string.bomb_threat)
                htmlAsSpanned = Html.fromHtml(htmlAsString)
                webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

            }

        }

    }
}