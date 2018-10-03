package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json

/**
 * Created by Sonphil on 09-03-18.
 */

class ApiSignetsModel<T : ApiSignetsData> {
    @Json(name = "d")
    var data: T? = null
}
