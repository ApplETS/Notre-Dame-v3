package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Sonphil on 09-03-18.
 */

@JsonClass(generateAdapter = true)
class ApiSignetsModel<T : ApiSignetsData> {
    @Json(name = "d")
    var data: T? = null
}
