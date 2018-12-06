package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEvaluationCours(
    @Json(name = "DateDebutEvaluation")
    var dateDebutEvaluation: String = "",
    @Json(name = "DateFinEvaluation")
    var dateFinEvaluation: String = "",
    @Json(name = "Enseignant")
    var enseignant: String = "",
    @Json(name = "EstComplete")
    var estComplete: Boolean = false,
    @Json(name = "Groupe")
    var groupe: String = "",
    @Json(name = "Sigle")
    var sigle: String = "",
    @Json(name = "TypeEvaluation")
    var typeEvaluation: String = ""
)