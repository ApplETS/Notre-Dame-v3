package model

import utils.date.ETSMobileDate

data class Seance(
    var dateDebut: ETSMobileDate,
    var dateFin: ETSMobileDate,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String,
    var sigleCours: String,
    var groupe: String,
    var session: String
)