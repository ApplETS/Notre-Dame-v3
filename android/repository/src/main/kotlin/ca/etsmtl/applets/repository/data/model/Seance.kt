package ca.etsmtl.applets.repository.data.model

import java.util.Date

data class Seance(
    var dateDebut: Date,
    var dateFin: Date,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String,
    var sigleCours: String,
    var groupe: String,
    var session: String
)