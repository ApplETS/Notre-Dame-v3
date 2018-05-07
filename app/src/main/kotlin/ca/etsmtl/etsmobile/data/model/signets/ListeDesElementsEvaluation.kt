package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class ListeDesElementsEvaluation(
    @Json(name = "__type") var type: String? = "",
    @Json(name = "noteACeJour") var noteACeJour: String? = "",
    @Json(name = "scoreFinalSur100") var scoreFinalSur100: String? = "",
    @Json(name = "moyenneClasse") var moyenneClasse: String? = "",
    @Json(name = "ecartTypeClasse") var ecartTypeClasse: String? = "",
    @Json(name = "medianeClasse") var medianeClasse: String? = "",
    @Json(name = "rangCentileClasse") var rangCentileClasse: String? = "",
    @Json(name = "noteACeJourElementsIndividuels") var noteACeJourElementsIndividuels: String? = "",
    @Json(name = "noteSur100PourElementsIndividuels") var noteSur100PourElementsIndividuels: String? = "",
    @Json(name = "liste") var liste: List<Evaluation?>? = listOf(),
    @Json(name = "erreur") var erreur: String? = ""
) : SignetsData() {
    override fun getError() = erreur
}