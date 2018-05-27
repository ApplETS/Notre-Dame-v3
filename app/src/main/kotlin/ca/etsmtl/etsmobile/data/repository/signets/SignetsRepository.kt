package ca.etsmtl.etsmobile.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.support.annotation.VisibleForTesting
import ca.etsmtl.etsmobile.AppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.model.signets.SignetsData
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel

/**
 * On a request, Signets's web service will return a 200 status code. If an error has occurred, it's
 * pushed down to the HTTP response. This abstract class provides a handy way to handle the error
 * whether it's a network error or an error from Signets.
 *
 * Created by Sonphil on 17-03-18.
 */
abstract class SignetsRepository(protected val appExecutors: AppExecutors) {

    /**
     * Returns the [ApiResponse] error. If a network error occurred, the network error
     * will be returned. If not, the error inside the payload will be returned. If the request was
     * successful, the [String] returned will be null.
     *
     * @return The [ApiResponse] error or null if there was no error
     */
    @VisibleForTesting
    fun getError(apiResponse: ApiResponse<out SignetsModel<out SignetsData>>?): String? {
        if (apiResponse == null)
            return "No Response"

        val error = !apiResponse.isSuccessful || apiResponse.body == null

        return when (error) {
            true -> apiResponse.errorMessage
            false -> getErrorInsideData(apiResponse.body)
        }
    }

    /**
     * Returns the value of the error's field
     *
     * @return The value of the error's field
     */
    private fun getErrorInsideData(signetsModel: SignetsModel<out SignetsData>?): String? {
        return when (signetsModel?.data == null) {
            true -> "No Data"
            false -> signetsModel!!.data!!.getError()
        }
    }

    /**
     * Transforms a [LiveData] created by SignetsApi into a new [LiveData] whose value is an
     * [ApiResponse] that exposes the error if the request failed.
     *
     * If the request succeeded, the value is the same [ApiResponse].
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    inline fun <reified T : SignetsData> transformApiLiveData(apiLiveData: LiveData<ApiResponse<SignetsModel<T>>>): LiveData<ApiResponse<SignetsModel<T>>> {
        return Transformations.switchMap(apiLiveData) { apiResponse ->
            val resultLiveData = MutableLiveData<ApiResponse<SignetsModel<T>>>()
            val errorStr = getError(apiResponse)

            if (errorStr.isNullOrEmpty()) {
                resultLiveData.value = apiResponse
            } else {
                resultLiveData.value = ApiResponse(Throwable(errorStr))
            }

            resultLiveData
        }
    }

    /**
     * Transforms a list [LiveData] to a single item [LiveData]. The item is the first element of
     * the list.
     *
     * @param listLiveData The list [LiveData] to transform
     * @return The transformed [LiveData] which will contain the first item. The item can be null
     * if the list was empty.
     */
    inline fun <reified T> getFirstItemLiveData(listLiveData: LiveData<List<T>>): LiveData<T> {
        val resultLiveData = MediatorLiveData<T>()
        resultLiveData.addSource(listLiveData, {
            resultLiveData.value = if (it != null && it.isNotEmpty()) {
                it[0]
            } else {
                null
            }

            resultLiveData.removeSource(listLiveData)
        })

        return resultLiveData
    }
}