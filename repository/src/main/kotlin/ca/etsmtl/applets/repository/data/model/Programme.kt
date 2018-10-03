package ca.etsmtl.applets.repository.data.model

data class Programme(
    var code: String,
    var libelle: String,
    var profil: String,
    var statut: String,
    var sessionDebut: String,
    var sessionFin: String,
    var moyenne: String,
    var nbEquivalences: Int,
    var nbCrsReussis: Int,
    var nbCrsEchoues: Int,
    var nbCreditsInscrits: Int,
    var nbCreditsCompletes: Int,
    var nbCreditsPotentiels: Int,
    var nbCreditsRecherche: Int
)