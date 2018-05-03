package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class ListeDesActivitesEtProf(
    @Json(name = "__type") val type: String? = "",
    @Json(name = "listeActivites") val listeActivites: List<Activite?>? = listOf(),
    @Json(name = "listeEnseignants") val listeEnseignants: List<Enseignant?>? = listOf(),
    @Json(name = "erreur") val erreur: String? = ""
) : SignetsData() {
    override fun getError() = erreur
}