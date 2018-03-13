package ca.etsmtl.etsmobile.data.model

import com.squareup.moshi.Json

/**
 * Created by Sonphil on 09-03-18.
 */
open class SignetsData {
    @Json(name = "erreur")
    var erreur: String? = null
}