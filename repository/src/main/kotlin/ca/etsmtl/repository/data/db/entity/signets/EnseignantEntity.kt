package ca.etsmtl.repository.data.db.entity.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class EnseignantEntity(
    var localBureau: String?,
    var telephone: String?,
    var enseignantPrincipal: String?,
    var nom: String?,
    var prenom: String?,
    @PrimaryKey
    var courriel: String
)