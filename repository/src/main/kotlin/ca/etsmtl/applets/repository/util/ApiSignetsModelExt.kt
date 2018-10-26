package ca.etsmtl.applets.repository.util

import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel

/**
 * The value of the error field
 */
internal val ApiSignetsModel<out ApiSignetsData>?.errorInsideData: String?
    get() {
        this?.data?.let { return it.erreur }

        return "No Data"
    }
