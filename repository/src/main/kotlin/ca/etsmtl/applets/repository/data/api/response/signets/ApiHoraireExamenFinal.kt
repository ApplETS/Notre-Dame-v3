package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiHoraireExamenFinal(
    @Json(name = "sigle") var sigle: String,
    @Json(name = "groupe") var groupe: String,
    @Json(name = "dateExamen") var dateExamen: String,
    @Json(name = "heureDebut") var heureDebut: String,
    @Json(name = "heureFin") var heureFin: String,
    @Json(name = "local") var local: String
)