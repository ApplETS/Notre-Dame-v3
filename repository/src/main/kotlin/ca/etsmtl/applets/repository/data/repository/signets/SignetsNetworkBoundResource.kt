package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.repository.NetworkBoundResource
import ca.etsmtl.applets.repository.util.networkOrSignetsError

/**
 * On a request, Signets's web service will return a 200 status code. If an error has occurred, it's
 * pushed down to the HTTP response. This abstract class provides a handy way to handle the error
 * whether it's a network error or an error from Signets.
 *
 * Created by Sonphil on 10-10-18.
 */
abstract class SignetsNetworkBoundResource<ResultType, T : ApiSignetsData>(appExecutors: AppExecutors) : NetworkBoundResource<ResultType, ApiSignetsModel<T>>(appExecutors) {
    override fun processCall(call: LiveData<ApiResponse<ApiSignetsModel<T>>>): LiveData<ApiResponse<ApiSignetsModel<T>>> {
        return Transformations.map(call) { apiResponse ->
            with (apiResponse.networkOrSignetsError) {
                when (isNullOrEmpty()) {
                    true -> apiResponse
                    false -> ApiResponse(kotlin.Throwable(this))
                }
            }
        }
    }

    final override fun saveCallResult(item: ApiSignetsModel<T>) {
        item.data?.let { saveSignetsData(it) }
    }

    abstract fun saveSignetsData(item: T)
}