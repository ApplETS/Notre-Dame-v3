package ca.etsmtl.repository.data.db.entity.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ActiviteEntity(
    @PrimaryKey
    var sigle: String,
    var groupe: String?,
    var jour: Int,
    var journee: String?,
    var codeActivite: String?,
    var nomActivite: String?,
    var activitePrincipale: String?,
    var heureDebut: String?,
    var heureFin: String?,
    var local: String?,
    var titreCours: String?
)