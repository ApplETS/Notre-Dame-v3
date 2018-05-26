package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["sigle", "groupe"])
data class SommaireElementsEvaluation(
    var sigle: String,
    var groupe: String,
    var noteACeJour: String,
    var scoreFinalSur100: String,
    var moyenneClasse: String,
    var ecartTypeClasse: String,
    var medianeClasse: String,
    var rangCentileClasse: String,
    var noteACeJourElementsIndividuels: String,
    var noteSur100PourElementsIndividuels: String
)