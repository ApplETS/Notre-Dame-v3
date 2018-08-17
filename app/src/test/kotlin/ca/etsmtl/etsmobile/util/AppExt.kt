package ca.etsmtl.etsmobile.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ca.etsmtl.etsmobile.presentation.App
import org.mockito.Mockito

/**
 * Created by Sonphil on 16-08-18.
 */

fun App.mockNetwork(connected: Boolean = true) {
    val connectivityManager = Mockito.mock(ConnectivityManager::class.java)
    Mockito.`when`(this.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    val activeNetworkInfo = Mockito.mock(NetworkInfo::class.java)
    Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(activeNetworkInfo)
    Mockito.`when`(activeNetworkInfo.isConnected).thenReturn(connected)
}