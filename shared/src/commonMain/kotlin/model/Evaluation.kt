package model

import utils.date.ETSMobileDate

data class Evaluation(
    var cours: String,
    var groupe: String,
    var session: String,
    var nom: String,
    var equipe: String,
    var dateCible: ETSMobileDate?,
    var note: String?,
    /**
     * This string represents the value the exam is graded on. The value can also contains the
     * bonus points separated by a plus sign  e.g. "50+3".
     */
    var corrigeSur: String?,
    var notePourcentage: String?,
    var ponderation: String,
    var moyenne: String?,
    var moyennePourcentage: String?,
    var ecartType: String?,
    var mediane: String?,
    var rangCentile: String?,
    var publie: Boolean,
    var messageDuProf: String,
    var ignoreDuCalcul: Boolean
)