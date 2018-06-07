package ca.etsmtl.repos.data.model.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ListeDesSeances(
    @Json(name = "__type") var type: String,
    @Json(name = "ListeDesSeances") var liste: List<Seance> = listOf(),
    @Json(name = "erreur") var erreur: String?
) : SignetsData() {
    override fun getError() = erreur
}