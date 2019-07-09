package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import android.content.BroadcastReceiver

class BroadCastReceiver: BroadcastReceiver()
{
    companion
    object { var wasNotConnected  =false}
    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            var isNotConnect = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (isNotConnect) {
                wasNotConnected = true
                Toast.makeText(context, "no may", Toast.LENGTH_SHORT).show()
            } else if (!isNotConnect && wasNotConnected)
                Toast.makeText(context, "connected may", Toast.LENGTH_SHORT).show()
        }
    }
}