package model

import utils.date.ETSMobileDate

data class Session(
    var abrege: String,
    var auLong: String,
    var dateDebut: ETSMobileDate,
    var dateFin: ETSMobileDate,
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