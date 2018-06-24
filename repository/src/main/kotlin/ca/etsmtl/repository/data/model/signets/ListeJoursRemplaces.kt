package ca.etsmtl.repository.data.model.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ListeJoursRemplaces(
    @Json(name = "__type") val type: String? = "",
    @Json(name = "listeJours") val listeJours: List<JourRemplace>? = listOf(),
    @Json(name = "erreur") val erreur: String? = ""
) : SignetsData() {
    override fun getError() = erreur
}