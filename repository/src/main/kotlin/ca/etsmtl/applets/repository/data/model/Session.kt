package ca.etsmtl.repository.data.model

data class Session(
    var abrege: String,
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