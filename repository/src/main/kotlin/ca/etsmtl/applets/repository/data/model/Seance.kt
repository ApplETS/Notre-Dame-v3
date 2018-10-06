package ca.etsmtl.applets.repository.data.model

import java.util.*

data class Seance(
        var dateDebut: Date,
        var dateFin: Date,
        var nomActivite: String,
        var local: String,
        var descriptionActivite: String,
        var libelleCours: String,
        var sigleCours: String,
        var session: String
)