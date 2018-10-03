package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiListeDesSeances(
    @Json(name = "__type") var type: String,
    @Json(name = "ListeDesSeances") var liste: List<ApiSeance> = listOf(),
    @Json(name = "erreur") var erreur: String?
) : ApiSignetsData() {
    override fun getError() = erreur
}