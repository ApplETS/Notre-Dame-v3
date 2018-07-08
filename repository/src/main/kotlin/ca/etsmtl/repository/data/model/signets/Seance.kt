package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Seance(
    @PrimaryKey
    var dateDebut: String,
    var dateFin: String,
    var coursGroupe: String,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String
)