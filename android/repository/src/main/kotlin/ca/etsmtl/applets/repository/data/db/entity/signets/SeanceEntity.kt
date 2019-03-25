package ca.etsmtl.applets.repository.data.db.entity.signets

import androidx.room.Entity

@Entity(primaryKeys = ["dateDebut", "local"])
data class SeanceEntity(
    var dateDebut: Long,
    var dateFin: Long,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String,
    var libelleCours: String,
    var sigleCours: String,
    var groupe: String,
    var session: String
)