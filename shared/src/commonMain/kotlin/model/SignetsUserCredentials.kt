package model

/**
 * User's credentials needed to access Signets' webservice
 *
 * Created by Sonphil on 28-02-18.
 */
@AndroidParcelize
data class SignetsUserCredentials(
    val codeAccesUniversel: UniversalCode,
    val motPasse: String
) : AndroidParcel {
    companion object {
        var INSTANCE: SignetsUserCredentials? = null
    }
}