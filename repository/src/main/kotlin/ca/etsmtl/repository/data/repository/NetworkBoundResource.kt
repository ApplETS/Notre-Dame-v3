/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.etsmtl.repository.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.model.Resource

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 *
 * @param <ResultType>
 * @param <RequestType>
 */
abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
protected constructor(private val appExecutors: AppExecutors) {

    companion object {
        const val MSG_NO_DATA_DB = "No data in DB"
        const val CALL_FAILED_WITHTOUT_ERROR_MSG = "The call failed, but there was no error message."
    }
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    if (newData != null) {
                        result.value = Resource.success(newData)
                    } else {
                        result.value = Resource.error(MSG_NO_DATA_DB, null)
                    }
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response != null && response.isSuccessful) {
                saveResultAndReInit(response)
            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData ->
                    val errorStr = response?.errorMessage ?: CALL_FAILED_WITHTOUT_ERROR_MSG
                    result.setValue(Resource.error(errorStr, newData))
                }
            }
        }
    }

    @MainThread
    private fun saveResultAndReInit(response: ApiResponse<RequestType>) {
        appExecutors.diskIO().execute {
            saveCallResult(response.body!!)
            appExecutors.mainThread().execute {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                result.addSource(loadFromDb()) { newData ->
                    result.setValue(newData?.let { Resource.success(it) })
                }
            }
        }
    }

    /**
     * Called when the fetch to the network has failed
     */
    protected fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    /**
     * Called when the data need to be saved to the DB
     *
     * The data has been fetch from the network and can, now, be saved to the disk.
     *
     * @param item The data to save
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * Called to determine whether the data should be fetched from the network or only from the DB
     *
     * @param data The data currently saved in the disk
     * @return Whether the data should be fetched from the network or only from the DB. If it's
     * false, the data cached in the DB will be returned. If it's true, the cached data will be
     * fetched from the DB and returned while the new data is being fetched from the network.
     */
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    /**
     * Called in order to get a [LiveData] that will be observed in order to get the data currently
     * cached in the disk
     *
     * @return The LiveData to observe in order to get the data currently cached in the disk
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * Called in order to get a [LiveData] that will be observed in order to get the data from the
     * webservice
     *
     * @return The LiveData to observe in order to get the data from the webservice
     */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
