package ca.etsmtl.applets.repository.data.db.entity.signets

import androidx.room.Entity
import androidx.room.PrimaryKey

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