package ca.etsmtl.applets.repository.data.api.requestbody.signets

import com.squareup.moshi.Json
import model.SignetsUserCredentials

/**
 * Created by Sonphil on 12-08-18.
 */

data class EtudiantRequestBody(
    @Json(name = "codeAccesUniversel") val codeAccesUniversel: String,
    @Json(name = "motPasse") val motPasse: String
) {
    constructor(signetsUserCredentials: SignetsUserCredentials) : this(
        signetsUserCredentials.codeAccesUniversel.value,
        signetsUserCredentials.motPasse
    )
}