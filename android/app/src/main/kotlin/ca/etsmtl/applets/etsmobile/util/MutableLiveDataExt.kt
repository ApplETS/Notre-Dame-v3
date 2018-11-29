package ca.etsmtl.applets.etsmobile.util

import androidx.lifecycle.MutableLiveData

/**
 * Created by Sonphil on 03-08-18.
 */

/**
 * Set the value to null to trigger a call
 *
 * Used for cases where T is Void, to make calls cleaner.
 */
fun MutableLiveData<Void>.call() {
    value = null
}