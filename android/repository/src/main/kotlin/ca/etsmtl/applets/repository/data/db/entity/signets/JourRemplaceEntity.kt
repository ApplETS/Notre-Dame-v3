package ca.etsmtl.applets.repository.data.db.entity.signets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JourRemplaceEntity(
    var dateOrigine: String,
    @PrimaryKey
    var dateRemplacement: String,
    var description: String?,
    var session: String
)