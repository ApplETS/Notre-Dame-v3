package ca.etsmtl.etsmobile.util

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.RequiresPermission

/**
 * Created by Sonphil on 18-05-18.
 */

@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isDeviceConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = cm.activeNetworkInfo

    return activeNetwork != null && activeNetwork.isConnected
}