package ca.etsmtl.repos.data.model.signets

import com.squareup.moshi.Json

/**
 * Created by Sonphil on 09-03-18.
 */

class SignetsModel<T : SignetsData> {
    @Json(name = "d")
    var data: T? = null
}
