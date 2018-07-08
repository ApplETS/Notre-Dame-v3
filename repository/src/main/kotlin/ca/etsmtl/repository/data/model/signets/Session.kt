package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Session(
    var abrege: String,
    @PrimaryKey
    var auLong: String,
    var dateDebut: String,
    var dateFin: String,
    var dateFinCours: String,
    var dateDebutChemiNot: String,
    var dateFinChemiNot: String,
    var dateDebutAnnulationAvecRemboursement: String,
    var dateFinAnnulationAvecRemboursement: String,
    var dateFinAnnulationAvecRemboursementNouveauxEtudiants: String,
    var dateDebutAnnulationSansRemboursementNouveauxEtudiants: String,
    var dateFinAnnulationSansRemboursementNouveauxEtudiants: String,
    var dateLimitePourAnnulerASEQ: String
)