package ca.etsmtl.applets.etsmobile.view

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

private fun getFixedContext(context: Context) = if (Build.VERSION.SDK_INT in 21..22) {
    context.createConfigurationContext(Configuration())
} else {
    context
}

/**
 * Subclass used to prevent crashes on Lollipop
 *
 * @see <a href="https://issuetracker.google.com/issues/141132133">https://issuetracker.google.com/issues/141132133</a>
 */
class LollipopFixedWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : WebView(getFixedContext(context), attrs, defStyleAttr, defStyleRes)