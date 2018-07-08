package ca.etsmtl.repository.data.api.response.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class ApiSession(
    @Json(name = "abrege") var abrege: String,
    @Json(name = "auLong") var auLong: String,
    @Json(name = "dateDebut") var dateDebut: String,
    @Json(name = "dateFin") var dateFin: String,
    @Json(name = "dateFinCours") var dateFinCours: String,
    @Json(name = "dateDebutChemiNot") var dateDebutChemiNot: String,
    @Json(name = "dateFinChemiNot") var dateFinChemiNot: String,
    @Json(name = "dateDebutAnnulationAvecRemboursement") var dateDebutAnnulationAvecRemboursement: String,
    @Json(name = "dateFinAnnulationAvecRemboursement") var dateFinAnnulationAvecRemboursement: String,
    @Json(name = "dateFinAnnulationAvecRemboursementNouveauxEtudiants") var dateFinAnnulationAvecRemboursementNouveauxEtudiants: String,
    @Json(name = "dateDebutAnnulationSansRemboursementNouveauxEtudiants") var dateDebutAnnulationSansRemboursementNouveauxEtudiants: String,
    @Json(name = "dateFinAnnulationSansRemboursementNouveauxEtudiants") var dateFinAnnulationSansRemboursementNouveauxEtudiants: String,
    @Json(name = "dateLimitePourAnnulerASEQ") var dateLimitePourAnnulerASEQ: String
)