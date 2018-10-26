package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListeDesSeances(
    @Json(name = "__type") var type: String,
    @Json(name = "ListeDesSeances") var liste: List<ApiSeance> = listOf(),
    @Json(name = "erreur") override var erreur: String?
) : ApiSignetsData()