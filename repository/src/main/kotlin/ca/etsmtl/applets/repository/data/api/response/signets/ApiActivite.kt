package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiActivite(
    @Json(name = "sigle") var sigle: String,
    @Json(name = "groupe") var groupe: String?,
    @Json(name = "jour") var jour: Int,
    @Json(name = "journee") var journee: String?,
    @Json(name = "codeActivite") var codeActivite: String?,
    @Json(name = "nomActivite") var nomActivite: String?,
    @Json(name = "activitePrincipale") var activitePrincipale: String?,
    @Json(name = "heureDebut") var heureDebut: String?,
    @Json(name = "heureFin") var heureFin: String?,
    @Json(name = "local") var local: String?,
    @Json(name = "titreCours") var titreCours: String?
)