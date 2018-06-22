package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Programme(
    @PrimaryKey
    @Json(name = "code") var code: String,
    @Json(name = "libelle") var libelle: String,
    @Json(name = "profil") var profil: String,
    @Json(name = "statut") var statut: String,
    @Json(name = "sessionDebut") var sessionDebut: String,
    @Json(name = "sessionFin") var sessionFin: String,
    @Json(name = "moyenne") var moyenne: String,
    @Json(name = "nbEquivalences") var nbEquivalences: Int,
    @Json(name = "nbCrsReussis") var nbCrsReussis: Int,
    @Json(name = "nbCrsEchoues") var nbCrsEchoues: Int,
    @Json(name = "nbCreditsInscrits") var nbCreditsInscrits: Int,
    @Json(name = "nbCreditsCompletes") var nbCreditsCompletes: Int,
    @Json(name = "nbCreditsPotentiels") var nbCreditsPotentiels: Int,
    @Json(name = "nbCreditsRecherche") var nbCreditsRecherche: Int
)