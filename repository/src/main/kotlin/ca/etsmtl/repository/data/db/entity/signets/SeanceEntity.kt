package ca.etsmtl.repository.data.db.entity.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class SeanceEntity(
    @PrimaryKey
    var dateDebut: String,
    var dateFin: String,
    var coursGroupe: String,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String
)