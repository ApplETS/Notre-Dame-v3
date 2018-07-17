package ca.etsmtl.repository.data.db.entity.signets

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class SeanceEntity(
    @PrimaryKey
    var dateDebut: String,
    var dateFin: String,
    @Embedded
    var cours: CoursEntity,
    var nomActivite: String,
    var local: String,
    var descriptionActivite: String
)