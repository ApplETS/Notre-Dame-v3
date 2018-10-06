package ca.etsmtl.applets.repository.data.db.entity.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class SeanceEntity(
    @PrimaryKey
    var dateDebut: Long,
    var dateFin: Long,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String,
    var sigleCours: String,
    var session: String
)