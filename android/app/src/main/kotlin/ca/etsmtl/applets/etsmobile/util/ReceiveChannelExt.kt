package ca.etsmtl.applets.etsmobile.util

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

/**
 * Created by Sonphil on 12-02-19.
 */

fun <T> ReceiveChannel<T>.toLiveData(): LiveData<T> = object : LiveData<T>(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    var job: Job? = null

    override fun onActive() {
        super.onActive()

        job = launch {
            for (value in this@toLiveData) {
                setValue(value)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()

        job?.cancel()
    }
}