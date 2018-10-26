/*
 * Copyright (C) 2017 The Android Open Source Project
 * Modifications copyright (C) 2018 Thanh-Son-Philippe Lam
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

package ca.etsmtl.applets.repository.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.etsmtl.applets.repository.data.api.ApiResponse
import retrofit2.Response

/**
 * Created by Sonphil on 14-03-18.
 */
class ApiUtil {
    companion object {
        fun <T> successCall(data: T): LiveData<ApiResponse<T>> {
            return createCall(Response.success(data))
        }

        fun <T> failCall(errorMsg: String): LiveData<ApiResponse<T>> {
            val data: MutableLiveData<ApiResponse<T>> = MutableLiveData()
            data.value = ApiResponse(Throwable(errorMsg))
            return data
        }

        fun <T> createCall(response: Response<T>): LiveData<ApiResponse<T>> {
            val data: MutableLiveData<ApiResponse<T>> = MutableLiveData()
            data.value = ApiResponse(response)
            return data
        }
    }
}