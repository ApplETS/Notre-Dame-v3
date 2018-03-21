package ca.etsmtl.etsmobile.data.model

import com.squareup.moshi.Json

/**
 * Created by Sonphil on 28-02-18.
 */
data class UserCredentials(
    @Json(name = "codeAccesUniversel") val codeAccesUniversel: String,
    @Json(name = "motPasse") val motPasse: String
)