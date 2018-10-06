package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSeance(
    @Json(name = "dateDebut") var dateDebut: String,
    @Json(name = "dateFin") var dateFin: String,
    @Json(name = "coursGroupe") var coursGroupe: String,
    @Json(name = "nomActivite") var nomActivite: String,
    @Json(name = "local") var local: String,
    @Json(name = "descriptionActivite") var descriptionActivite: String,
    @Json(name = "libelleCours") var libelleCours: String
)