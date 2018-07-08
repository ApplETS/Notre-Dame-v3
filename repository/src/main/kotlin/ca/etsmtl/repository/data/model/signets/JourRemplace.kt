package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class JourRemplace(
    var dateOrigine: String,
    @PrimaryKey
    var dateRemplacement: String,
    var description: String?
)