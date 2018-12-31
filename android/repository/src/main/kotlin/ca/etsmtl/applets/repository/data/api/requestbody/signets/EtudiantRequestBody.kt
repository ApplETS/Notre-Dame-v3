package ca.etsmtl.applets.repository.data.api.requestbody.signets

import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import com.squareup.moshi.Json

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