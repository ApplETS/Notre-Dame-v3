package data.api.requestbody.signets

import model.SignetsUserCredentials

/**
 * Created by Sonphil on 12-08-18.
 */

data class EtudiantRequestBody(
    val codeAccesUniversel: String,
    val motPasse: String
) {
    constructor(signetsUserCredentials: SignetsUserCredentials) : this(
        signetsUserCredentials.codeAccesUniversel.value,
        signetsUserCredentials.motPasse
    )
}