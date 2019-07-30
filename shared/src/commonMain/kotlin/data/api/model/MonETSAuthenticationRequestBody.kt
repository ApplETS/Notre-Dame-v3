package data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonETSAuthenticationRequestBody(
    @SerialName("Password")
    val password: String,
    @SerialName("Username")
    val username: String
)