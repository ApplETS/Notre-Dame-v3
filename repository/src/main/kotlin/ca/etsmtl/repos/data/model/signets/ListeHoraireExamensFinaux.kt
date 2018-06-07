package ca.etsmtl.repos.data.model.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ListeHoraireExamensFinaux(
    @Json(name = "__type") var type: String? = "",
    @Json(name = "listeHoraire") var listeHoraire: List<HoraireExamenFinal?>? = listOf(),
    @Json(name = "erreur") var erreur: String? = ""
) : SignetsData() {
    override fun getError(): String? {
        return erreur
    }
}