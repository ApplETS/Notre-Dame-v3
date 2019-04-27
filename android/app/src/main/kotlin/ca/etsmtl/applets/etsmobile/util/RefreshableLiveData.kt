package ca.etsmtl.applets.etsmobile.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Created by Sonphil on 10-01-19.
 */

abstract class RefreshableLiveData<T> : MediatorLiveData<T>() {
    private var source: LiveData<T>? = null
        set(value) {
            field?.let { removeSource(it) }
            field = value
        }

    fun refresh() {
        updateSource().let {
            source = it

            addSource(it) { value ->
                this.value = value
            }
        }
    }

    fun refreshIfValueIsNull() {
        if (value == null) {
            refresh()
        }
    }

    abstract fun updateSource(): LiveData<T>
}