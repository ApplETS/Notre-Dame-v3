package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class HoraireExamenFinal(
    @PrimaryKey
    var sigle: String,
    var groupe: String?,
    var dateExamen: String?,
    var heureDebut: String?,
    var heureFin: String?,
    var local: String?
)