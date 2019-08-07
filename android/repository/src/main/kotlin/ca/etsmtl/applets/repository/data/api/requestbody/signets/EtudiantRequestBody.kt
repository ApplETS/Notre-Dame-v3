package ca.etsmtl.applets.repository.data.api.requestbody.signets

import com.squareup.moshi.Json
import model.UserCredentials

/**
 * Created by Sonphil on 12-08-18.
 */

data class EtudiantRequestBody(
    @Json(name = "codeAccesUniversel") val codeAccesUniversel: String,
    @Json(name = "motPasse") val motPasse: String
) {
    constructor(userCredentials: UserCredentials) : this(
        userCredentials.universalCode.value,
        userCredentials.password
    )
}