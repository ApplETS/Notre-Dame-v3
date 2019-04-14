package ca.etsmtl.applets.repository.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import model.Resource

/**
 * Created by Sonphil on 01-11-18.
 */

infix fun <A, B> LiveData<A?>.zipTo(b: LiveData<B?>): LiveData<Pair<A?, B?>> = MediatorLiveData<Pair<A?, B?>>().apply {
    zip(this@zipTo, b)
}

infix fun <A, B> LiveData<Resource<A>>.zipResourceTo(b: LiveData<Resource<B>>): LiveData<Resource<Pair<A?, B?>>> = MediatorLiveData<Resource<Pair<A?, B?>>>().apply {
    zipResource(this@zipResourceTo, b)
}