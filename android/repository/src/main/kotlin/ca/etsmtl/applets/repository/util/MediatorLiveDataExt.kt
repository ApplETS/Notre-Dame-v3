package ca.etsmtl.applets.repository.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import model.Resource

/**
 * Created by Sonphil on 01-11-18.
 */

internal fun <A, B> MediatorLiveData<Pair<A?, B?>>.zip(a: LiveData<A>, b: LiveData<B>) {
    var lastA: A? = null
    var lastB: B? = null

    addSource(a) {
        lastA = it
        value = Pair(lastA, lastB)
    }

    addSource(b) {
        lastB = it
        value = Pair(lastA, lastB)
    }
}

internal fun <A, B> MediatorLiveData<Resource<Pair<A?, B?>>>.zipResource(a: LiveData<Resource<A>>, b: LiveData<Resource<B>>) {
    var lastA: Resource<A>? = null
    var lastB: Resource<B>? = null

    value = Resource.loading(null)

    fun updateValue() {
        val pair = Pair(lastA?.data, lastB?.data)

        value = if (lastA == null || lastB == null) {
            Resource.loading(pair)
        } else if (lastA?.status == Resource.Status.ERROR) {
            lastA?.copyStatusAndMessage(pair)
        } else if (lastB?.status == Resource.Status.ERROR) {
            lastB?.copyStatusAndMessage(pair)
        } else if (lastA?.status == Resource.Status.LOADING || lastB?.status == Resource.Status.LOADING) {
            Resource.loading(pair)
        } else {
            Resource.success(pair)
        }
    }

    addSource(a) {
        lastA = it
        updateValue()
    }

    addSource(b) {
        lastB = it
        updateValue()
    }
}