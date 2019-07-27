package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import android.content.BroadcastReceiver
import ca.etsmtl.applets.etsmobile.R

class BroadCastReceiver: BroadcastReceiver() {


    companion
    object { var wasNotConnected  =false}
    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            var isNotConnect = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (isNotConnect && !wasNotConnected ) {
                wasNotConnected = true
                Toast.makeText(context, context.getString(R.string.error_no_internet_connection), Toast.LENGTH_SHORT).show()
            } else if (!isNotConnect && wasNotConnected) {
                Toast.makeText(context, context.getString(R.string.internet_connected), Toast.LENGTH_SHORT).show()
                wasNotConnected=false
            }
        }
    }
}