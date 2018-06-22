package ca.etsmtl.repository.data.model.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ListeDeSessions(
    @Json(name = "__type") val type: String = "",
    @Json(name = "liste") val liste: List<Session> = listOf(),
    @Json(name = "erreur") val erreur: String? = ""
) : SignetsData() {
    override fun getError() = erreur
}