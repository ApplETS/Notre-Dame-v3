package ca.etsmtl.applets.etsmobile.extension

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import ca.etsmtl.applets.etsmobilenotifications.NotificationsLoginManager
import model.UserCredentials

/**
 * Created by Sonphil on 18-05-18.
 */

/**
 * Returns true when dark theme is on, false otherwise.
 */
inline val Context.isDarkMode: Boolean
    get() {
        val uiMode = resources.configuration.uiMode

        return uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

/**
 * Check if the device is connected
 *
 * @return True if the device is connected
 */
@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isDeviceConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = cm.activeNetworkInfo

    return activeNetwork != null && activeNetwork.isConnected
}

/**
 * Uses [ContextCompat] to return a color associated with a particular resource ID
 */
@ColorInt
fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

/**
 * Returns color from attribute
 */
@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

/**
 * Returns the height of the status bar
 */
fun Context.getStatusBarHeight() = resources
    .getAndroidDimensionInPixelSize("status_bar_height") ?: 0

/**
 * Provides credentials to notifications library and lets it register the user to AWS SNS
 */
fun Context.loginNotifications() {
    UserCredentials.INSTANCE?.let { creds ->
        NotificationsLoginManager.login(this, creds.universalCode.value, creds.domain)
    }
}