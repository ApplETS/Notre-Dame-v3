package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Activite(
    @Json(name = "sigle") val sigle: String? = "",
    @Json(name = "groupe") val groupe: String? = "",
    @Json(name = "jour") val jour: Int,
    @Json(name = "journee") val journee: String? = "",
    @Json(name = "codeActivite") val codeActivite: String? = "",
    @Json(name = "nomActivite") val nomActivite: String? = "",
    @Json(name = "activitePrincipale") val activitePrincipale: String? = "",
    @Json(name = "heureDebut") val heureDebut: String? = "",
    @Json(name = "heureFin") val heureFin: String? = "",
    @Json(name = "local") val local: String? = "",
    @Json(name = "titreCours") val titreCours: String? = ""
)