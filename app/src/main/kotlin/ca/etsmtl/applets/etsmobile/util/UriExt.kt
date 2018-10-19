package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 14-08-18.
 */

fun Uri.openWithChromeCustomTabs(context: Context, @ColorRes colorId: Int = R.color.colorPrimary) {
    with (CustomTabsIntent.Builder().setToolbarColor(ContextCompat.getColor(context, colorId)).build()) {
        launchUrl(context, this@openWithChromeCustomTabs)
    }
}