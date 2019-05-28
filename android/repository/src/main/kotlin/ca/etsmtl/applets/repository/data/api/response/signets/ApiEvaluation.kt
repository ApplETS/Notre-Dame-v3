package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEvaluation(
    @Json(name = "coursGroupe") var coursGroupe: String,
    @Json(name = "nom") var nom: String,
    @Json(name = "equipe") var equipe: String,
    @Json(name = "dateCible") var dateCible: String,
    @Json(name = "note") var note: String?,
    /**
     * This string represents the value the exam is corrected on. The value can also contains the
     * bonus points separated by a plus sign  e.g. "50+3".
     */
    @Json(name = "corrigeSur") var corrigeSur: String,
    @Json(name = "ponderation") var ponderation: String,
    @Json(name = "moyenne") var moyenne: String,
    @Json(name = "ecartType") var ecartType: String,
    @Json(name = "mediane") var mediane: String,
    @Json(name = "rangCentile") var rangCentile: String,
    @Json(name = "publie") var publie: String,
    @Json(name = "messageDuProf") var messageDuProf: String,
    @Json(name = "ignoreDuCalcul") var ignoreDuCalcul: String
)