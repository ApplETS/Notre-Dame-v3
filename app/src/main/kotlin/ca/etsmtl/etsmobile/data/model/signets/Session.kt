package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Session(
    @Json(name = "abrege") val abrege: String = "",
    @Json(name = "auLong") val auLong: String = "",
    @Json(name = "dateDebut") val dateDebut: String = "",
    @Json(name = "dateFin") val dateFin: String = "",
    @Json(name = "dateFinCours") val dateFinCours: String = "",
    @Json(name = "dateDebutChemiNot") val dateDebutChemiNot: String = "",
    @Json(name = "dateFinChemiNot") val dateFinChemiNot: String = "",
    @Json(name = "dateDebutAnnulationAvecRemboursement") val dateDebutAnnulationAvecRemboursement: String = "",
    @Json(name = "dateFinAnnulationAvecRemboursement") val dateFinAnnulationAvecRemboursement: String = "",
    @Json(name = "dateFinAnnulationAvecRemboursementNouveauxEtudiants") val dateFinAnnulationAvecRemboursementNouveauxEtudiants: String = "",
    @Json(name = "dateDebutAnnulationSansRemboursementNouveauxEtudiants") val dateDebutAnnulationSansRemboursementNouveauxEtudiants: String = "",
    @Json(name = "dateFinAnnulationSansRemboursementNouveauxEtudiants") val dateFinAnnulationSansRemboursementNouveauxEtudiants: String = "",
    @Json(name = "dateLimitePourAnnulerASEQ") val dateLimitePourAnnulerASEQ: String = ""
)