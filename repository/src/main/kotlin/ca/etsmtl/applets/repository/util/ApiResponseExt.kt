package ca.etsmtl.applets.repository.util

import androidx.annotation.VisibleForTesting
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel

/**
 * The [ApiResponse] error. If a network error occurred, the network error
 * will be returned. If not, the error inside the payload will be returned. If the request was
 * successful, the [String] will be null.
 */
@VisibleForTesting
internal val ApiResponse<out ApiSignetsModel<out ApiSignetsData>>?.networkOrSignetsError: String?
    get() {
        if (this == null)
            return "No Response"

        val error = !isSuccessful || body == null

        return when (error) {
            true -> errorMessage
            false -> body.errorInsideData
        }
    }