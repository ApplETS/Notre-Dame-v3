package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiListeJoursRemplaces(
    @Json(name = "__type") val type: String? = "",
    @Json(name = "listeJours") val listeJours: List<ApiJourRemplace>? = listOf(),
    @Json(name = "erreur") val erreur: String? = ""
) : ApiSignetsData() {
    override fun getError() = erreur
}