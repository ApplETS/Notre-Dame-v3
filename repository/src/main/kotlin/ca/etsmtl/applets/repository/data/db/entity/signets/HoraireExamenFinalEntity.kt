package ca.etsmtl.applets.repository.data.db.entity.signets

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["dateExamen", "heureDebut"])
data class HoraireExamenFinalEntity(
    var sigle: String,
    var groupe: String,
    var dateExamen: String,
    var heureDebut: String,
    var heureFin: String,
    var local: String,
    var session: String
)