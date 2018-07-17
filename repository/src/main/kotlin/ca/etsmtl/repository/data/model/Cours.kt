package ca.etsmtl.repository.data.model

data class Cours(
    var sigle: String,
    var groupe: String,
    var session: String,
    var programmeEtudes: String,
    var cote: String,
    var nbCredits: Int = 0,
    var titreCours: String
)