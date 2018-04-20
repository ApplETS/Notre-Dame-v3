package ca.etsmtl.etsmobile.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ca.etsmtl.etsmobile.AppExecutors
import ca.etsmtl.etsmobile.data.db.AppDatabase
import javax.inject.Inject

/**
 * Created by Sonphil on 20-04-18.
 */

class LogOutRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: AppDatabase
) {
    fun clearDb(): LiveData<Boolean> {
        val clearFinished = MutableLiveData<Boolean>()

        clearFinished.value = false

        appExecutors.diskIO().execute {
            db.clearAllTables()
            clearFinished.postValue(true)
        }

        return clearFinished
    }
}