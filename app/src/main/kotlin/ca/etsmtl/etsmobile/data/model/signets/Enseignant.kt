package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Enseignant(
    @Json(name = "localBureau") var localBureau: String?,
    @Json(name = "telephone") var telephone: String?,
    @Json(name = "enseignantPrincipal") var enseignantPrincipal: String?,
    @Json(name = "nom") var nom: String?,
    @Json(name = "prenom") var prenom: String?,
    @PrimaryKey
    @Json(name = "courriel") var courriel: String
)