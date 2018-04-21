package ca.etsmtl.etsmobile.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Sonphil on 21-04-18.
 */

class NetworkUtils {
    companion object {

        /**
         * Returns true if network is available
         *
         * @param context
         * @return true is network is available
         */
        fun isDeviceConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}