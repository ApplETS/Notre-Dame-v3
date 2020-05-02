package data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WhatsNewItems(
        @SerialName("_id")
        var id: String?,
        @SerialName("title")
        var title: String?,
        @SerialName("description")
        var description: String?,
        @SerialName("version")
        var version: String ?
)