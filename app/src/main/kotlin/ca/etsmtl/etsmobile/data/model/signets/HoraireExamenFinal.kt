package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class HoraireExamenFinal(
    @Json(name = "sigle") var sigle: String? = "",
    @Json(name = "groupe") var groupe: String? = "",
    @Json(name = "dateExamen") var dateExamen: String? = "",
    @Json(name = "heureDebut") var heureDebut: String? = "",
    @Json(name = "heureFin") var heureFin: String? = "",
    @Json(name = "local") var local: String? = ""
)