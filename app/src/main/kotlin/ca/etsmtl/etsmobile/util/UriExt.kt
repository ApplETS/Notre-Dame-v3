package ca.etsmtl.etsmobile.util

import android.content.Context
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import ca.etsmtl.etsmobile.R

/**
 * Created by Sonphil on 14-08-18.
 */

fun Uri.openWithChromeCustomTabs(context: Context, @ColorRes colorId: Int = R.color.colorPrimary) {
    with (CustomTabsIntent.Builder().setToolbarColor(ContextCompat.getColor(context, colorId)).build()) {
        launchUrl(context, this@openWithChromeCustomTabs)
    }
}