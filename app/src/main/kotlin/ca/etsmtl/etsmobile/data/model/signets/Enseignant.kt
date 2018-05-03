package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Enseignant(
    @Json(name = "localBureau") val localBureau: String? = "",
    @Json(name = "telephone") val telephone: String? = "",
    @Json(name = "enseignantPrincipal") val enseignantPrincipal: String? = "",
    @Json(name = "nom") val nom: String? = "",
    @Json(name = "prenom") val prenom: String? = "",
    @Json(name = "courriel") val courriel: String? = ""
)