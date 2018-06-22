package ca.etsmtl.repository.data.model.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ListeProgrammes(
    @Json(name = "__type") var type: String? = "",
    @Json(name = "liste") var liste: List<Programme> = listOf(),
    @Json(name = "erreur") var erreur: String? = ""
) : SignetsData() {
    override fun getError() = erreur
}