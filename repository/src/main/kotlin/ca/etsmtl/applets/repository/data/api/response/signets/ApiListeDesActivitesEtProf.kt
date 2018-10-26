package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListeDesActivitesEtProf(
    @Json(name = "__type") val type: String? = "",
    @Json(name = "listeActivites") val listeActivites: List<ApiActivite>? = listOf(),
    @Json(name = "listeEnseignants") val listeEnseignants: List<ApiEnseignant>? = listOf(),
    @Json(name = "erreur") override val erreur: String? = ""
) : ApiSignetsData()