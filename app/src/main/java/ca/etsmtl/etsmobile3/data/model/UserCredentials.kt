package ca.etsmtl.etsmobile3.data.model

import com.squareup.moshi.Json

/**
 * Created by Sonphil on 28-02-18.
 */
data class UserCredentials(
        @Json(name = "codeAccesUniversel") val universalCode: String,
        @Json(name = "motPasse") val password: String
)