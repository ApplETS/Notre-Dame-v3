package ca.etsmtl.applets.repository.data.api.response.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class ApiSeance(
    @Json(name = "dateDebut") var dateDebut: String,
    @Json(name = "dateFin") var dateFin: String,
    @Json(name = "coursGroupe") var coursGroupe: String,
    @Json(name = "nomActivite") var nomActivite: String,
    @Json(name = "local") var local: String,
    @Json(name = "descriptionActivite") var descriptionActivite: String,
    @Json(name = "libelleCours") var libelleCours: String
)