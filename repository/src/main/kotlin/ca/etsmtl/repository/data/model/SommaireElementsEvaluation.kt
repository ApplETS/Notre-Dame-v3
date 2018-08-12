package ca.etsmtl.repository.data.model

data class SommaireElementsEvaluation(
    var sigleCours: String,
    var session: String,
    var noteACeJour: String,
    var scoreFinalSur100: String,
    var moyenneClasse: String,
    var ecartTypeClasse: String,
    var medianeClasse: String,
    var rangCentileClasse: String,
    var noteACeJourElementsIndividuels: String,
    var noteSur100PourElementsIndividuels: String
)