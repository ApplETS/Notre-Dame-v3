package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListeDesElementsEvaluation(
    @Json(name = "__type") var type: String,
    @Json(name = "noteACeJour") var noteACeJour: String,
    @Json(name = "scoreFinalSur100") var scoreFinalSur100: String,
    @Json(name = "moyenneClasse") var moyenneClasse: String,
    @Json(name = "ecartTypeClasse") var ecartTypeClasse: String,
    @Json(name = "medianeClasse") var medianeClasse: String,
    @Json(name = "rangCentileClasse") var rangCentileClasse: String,
    @Json(name = "noteACeJourElementsIndividuels") var noteACeJourElementsIndividuels: String,
    @Json(name = "noteSur100PourElementsIndividuels") var noteSur100PourElementsIndividuels: String,
    @Json(name = "liste") var liste: List<ApiEvaluation> = listOf(),
    @Json(name = "erreur") var erreur: String
) : ApiSignetsData() {
    override fun getError() = erreur
}