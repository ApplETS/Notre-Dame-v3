package ca.etsmtl.repository.data.api.response.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiCours(
    @Json(name = "sigle") var sigle: String,
    @Json(name = "groupe") var groupe: String,
    @Json(name = "session") var session: String,
    @Json(name = "programmeEtudes") var programmeEtudes: String,
    @Json(name = "cote") var cote: String?,
    @Json(name = "nbCredits") var nbCredits: Int = 0,
    @Json(name = "titreCours") var titreCours: String
) {

    /**
     * Returns true if the course has a valid session (i.e. H2018)
     *
     * @return True if the course has a valid session (i.e. H2018)
     */
    fun hasValidSession() = session.matches(Regex("[aheéAHEÉ]\\d{4}"))
}