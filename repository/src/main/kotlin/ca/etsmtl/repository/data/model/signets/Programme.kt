package ca.etsmtl.repository.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Programme(
    @PrimaryKey
    var code: String,
    var libelle: String,
    var profil: String,
    var statut: String,
    var sessionDebut: String,
    var sessionFin: String,
    var moyenne: String,
    var nbEquivalences: Int,
    var nbCrsReussis: Int,
    var nbCrsEchoues: Int,
    var nbCreditsInscrits: Int,
    var nbCreditsCompletes: Int,
    var nbCreditsPotentiels: Int,
    var nbCreditsRecherche: Int
)