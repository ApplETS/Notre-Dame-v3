package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Enseignant(
    var localBureau: String?,
    var telephone: String?,
    var enseignantPrincipal: String?,
    var nom: String?,
    var prenom: String?,
    @PrimaryKey
    var courriel: String
)