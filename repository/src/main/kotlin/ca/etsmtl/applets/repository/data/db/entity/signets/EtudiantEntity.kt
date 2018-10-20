package ca.etsmtl.applets.repository.data.db.entity.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class EtudiantEntity(
    var type: String,
    var nom: String,
    var prenom: String,
    @PrimaryKey
    var codePerm: String,
    var soldeTotal: String,
    var masculin: Boolean
)
