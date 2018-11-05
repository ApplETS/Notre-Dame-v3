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

package ca.etsmtl.applets.repository.data.model

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> error(msg: String, data: T?): Resource<T> = Resource(Status.ERROR, data, msg)
        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
    }

    fun <T> copyStatusAndMessage(data: T?): Resource<T> = Resource(status, data, message)

    override fun equals(other: Any?): Boolean {
        // If this and other point to the same object ...
        if (this === other) {
            return true
        }

        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val res = other as Resource<*>

        if (status != res.status) {
            return false
        }

        if (if (message != null) message != res.message else res.message != null) {
            return false
        }

        return if (data != null) data == res.data else res.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }
}
