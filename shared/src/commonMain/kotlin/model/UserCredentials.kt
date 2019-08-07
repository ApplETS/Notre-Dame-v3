package model

/**
 * User's credentials needed to access Signets' webservice
 *
 * Created by Sonphil on 28-02-18.
 */
@AndroidParcelize
data class UserCredentials(
    val universalCode: UniversalCode,
    val password: String,
    val domain: String
) : AndroidParcel {
    companion object {
        var INSTANCE: UserCredentials? = null
    }
}