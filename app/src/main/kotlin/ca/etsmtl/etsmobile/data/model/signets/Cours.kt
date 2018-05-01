package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Cours(
    @PrimaryKey
    @Json(name = "sigle") val sigle: String? = "",
    @Json(name = "groupe") val groupe: String? = "",
    @Json(name = "session") val session: String? = "",
    @Json(name = "programmeEtudes") val programmeEtudes: String? = "",
    @Json(name = "cote") val cote: String? = "",
    @Json(name = "nbCredits") val nbCredits: Int = 0,
    @Json(name = "titreCours") val titreCours: String? = ""
)