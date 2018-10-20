package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListeDeSessions(
    @Json(name = "__type") val type: String = "",
    @Json(name = "liste") val liste: List<ApiSession> = listOf(),
    @Json(name = "erreur") override val erreur: String? = ""
) : ApiSignetsData()