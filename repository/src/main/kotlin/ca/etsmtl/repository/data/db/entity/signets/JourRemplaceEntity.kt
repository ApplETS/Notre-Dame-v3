package ca.etsmtl.repository.data.db.entity.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class JourRemplaceEntity(
    var dateOrigine: String,
    @PrimaryKey
    var dateRemplacement: String,
    var description: String?
)