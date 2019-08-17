package model

import com.soywiz.klock.DateTime

data class Seance(
    var dateDebut: DateTime,
    var dateFin: DateTime,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String,
    var sigleCours: String,
    var groupe: String,
    var session: String
)