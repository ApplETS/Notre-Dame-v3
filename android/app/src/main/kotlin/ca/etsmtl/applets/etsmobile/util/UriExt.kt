package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 14-08-18.
 */

/**
 * Open an URI using Chrome Custom Tabs or another browser if Chrome is not available
 *
 * @param context
 * @param colorId The resource ID of the color used for the toolbar
 * @return True if this could be opened whether it was with Chrome Custom Tabs or another browser
 */
fun Uri.open(context: Context, @ColorRes colorId: Int = R.color.colorPrimary): Boolean {
    try {
        with(CustomTabsIntent.Builder().setToolbarColor(context.getColorCompat(colorId)).build()) {
            launchUrl(context, this@open)

            return true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    try {
        with(Intent(Intent.ACTION_VIEW, this)) {
            if (resolveActivity(context.packageManager) != null) {
                context.startActivity(this)

                return true
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}