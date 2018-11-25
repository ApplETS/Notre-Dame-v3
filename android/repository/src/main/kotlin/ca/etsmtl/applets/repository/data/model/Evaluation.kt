package ca.etsmtl.applets.repository.data.model

data class Evaluation(
    var cours: String,
    var groupe: String,
    var session: String,
    var nom: String,
    var equipe: String,
    var dateCible: String,
    var note: String,
    var corrigeSur: String,
    var notePourcentage: String,
    var ponderation: String,
    var moyenne: String,
    var moyennePourcentage: String,
    var ecartType: String,
    var mediane: String,
    var rangCentile: String,
    var publie: Boolean,
    var messageDuProf: String,
    var ignoreDuCalcul: Boolean
)