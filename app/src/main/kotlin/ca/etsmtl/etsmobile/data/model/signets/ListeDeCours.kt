package ca.etsmtl.etsmobile.data.model.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ListeDeCours(
    @Json(name = "__type") val type: String? = "",
    @Json(name = "liste") val liste: List<Cours>? = listOf(),
    @Json(name = "erreur") val erreur: String? = ""
) : SignetsData() {
    override fun getError() = erreur
}