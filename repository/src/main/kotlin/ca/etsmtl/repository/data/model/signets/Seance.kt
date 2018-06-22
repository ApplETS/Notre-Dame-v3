package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Seance(
    @PrimaryKey
    @Json(name = "dateDebut") var dateDebut: String,
    @Json(name = "dateFin") var dateFin: String,
    @Json(name = "coursGroupe") var coursGroupe: String,
    @Json(name = "nomActivite") var nomActivite: String,
    @Json(name = "local") var local: String,
    @Json(name = "descriptionActivite") var descriptionActivite: String,
    @Json(name = "libelleCours") var libelleCours: String
)