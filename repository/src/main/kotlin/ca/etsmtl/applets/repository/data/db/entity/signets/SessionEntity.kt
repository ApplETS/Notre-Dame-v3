package ca.etsmtl.applets.repository.data.db.entity.signets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionEntity(
    var abrege: String,
    @PrimaryKey
    var auLong: String,
    var dateDebut: Long,
    var dateFin: Long,
    var dateFinCours: Long,
    var dateDebutChemiNot: Long,
    var dateFinChemiNot: Long,
    var dateDebutAnnulationAvecRemboursement: Long,
    var dateFinAnnulationAvecRemboursement: Long,
    var dateFinAnnulationAvecRemboursementNouveauxEtudiants: Long,
    var dateDebutAnnulationSansRemboursementNouveauxEtudiants: Long,
    var dateFinAnnulationSansRemboursementNouveauxEtudiants: Long,
    var dateLimitePourAnnulerASEQ: Long
)