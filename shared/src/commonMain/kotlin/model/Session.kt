package model

data class Session(
    var abrege: String,
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