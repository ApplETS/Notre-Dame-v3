package data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonETSUser(
    @SerialName("Domaine")
    var domaine: String = "",
    @SerialName("TypeUsagerId")
    var typeUsagerId: Int = 0,
    @SerialName("Username")
    var username: String = ""
)