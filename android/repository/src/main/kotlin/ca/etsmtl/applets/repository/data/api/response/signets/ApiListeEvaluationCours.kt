package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListeEvaluationCours(
    @Json(name = "__type")
    var type: String = "",
    @Json(name = "erreur")
    override var erreur: String?,
    @Json(name = "listeEvaluations")
    var listeApiEvaluations: List<ApiEvaluationCours> = listOf()
) : ApiSignetsData()