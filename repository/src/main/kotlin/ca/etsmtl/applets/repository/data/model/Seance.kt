package ca.etsmtl.applets.repository.data.model

data class Seance(
    var dateDebut: String,
    var dateFin: String,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String,
    var sigleCours: String,
    var session: String
)