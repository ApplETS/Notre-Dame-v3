package ca.etsmtl.etsmobile.data.repository.signets

import android.support.annotation.VisibleForTesting
import ca.etsmtl.etsmobile.AppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.model.signets.SignetsData
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel

/**
 * Created by Sonphil on 17-03-18.
 */
abstract class SignetsRepository(protected val appExecutors: AppExecutors) {
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun getError(apiResponse: ApiResponse<out SignetsModel<out SignetsData>>?): String? {
        if (apiResponse == null)
            return "No Response"

        val error = !apiResponse.isSuccessful || apiResponse.body == null

        return when (error) {
            true -> apiResponse.errorMessage
            false -> getErrorInsideData(apiResponse.body)
        }
    }

    private fun getErrorInsideData(signetsModel: SignetsModel<out SignetsData>?): String? {
        return when (signetsModel?.data == null) {
            true -> "No Data"
            false -> signetsModel!!.data!!.getError()
        }
    }
}