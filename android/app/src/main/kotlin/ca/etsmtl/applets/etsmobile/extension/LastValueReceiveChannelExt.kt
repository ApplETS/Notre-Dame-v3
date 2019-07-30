package ca.etsmtl.applets.etsmobile.extension

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import utils.LastValueReceiveChannel

/**
 * Created by Sonphil on 29-07-19.
 */

fun <T> LastValueReceiveChannel<T>.toLiveData(): LiveData<T> = object : LiveData<T>(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    var job: Job? = null

    override fun onActive() {
        super.onActive()

        job = launch {
            value = lastValue

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