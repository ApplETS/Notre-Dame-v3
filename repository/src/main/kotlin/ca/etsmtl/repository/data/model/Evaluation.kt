package ca.etsmtl.repository.data.model

data class Evaluation(
    var coursGroupe: String,
    var nom: String,
    var equipe: String?,
    var dateCible: String?,
    var note: String?,
    var corrigeSur: String?,
    var ponderation: String?,
    var moyenne: String?,
    var ecartType: String?,
    var mediane: String?,
    var rangCentile: String?,
    var publie: String?,
    var messageDuProf: String?,
    var ignoreDuCalcul: String?
)