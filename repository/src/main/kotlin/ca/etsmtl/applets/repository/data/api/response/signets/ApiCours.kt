package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCours(
    @Json(name = "sigle") var sigle: String,
    @Json(name = "groupe") var groupe: String,
    @Json(name = "session") var session: String,
    @Json(name = "programmeEtudes") var programmeEtudes: String,
    @Json(name = "cote") var cote: String?,
    @Json(name = "nbCredits") var nbCredits: Int = 0,
    @Json(name = "titreCours") var titreCours: String
)