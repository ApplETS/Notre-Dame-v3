package model

import utils.date.ETSMobileDate

data class EvaluationCours(
    var session: String,
    var dateDebutEvaluation: ETSMobileDate,
    var dateFinEvaluation: ETSMobileDate,
    var enseignant: String,
    var estComplete: Boolean = false,
    var groupe: String,
    var sigle: String,
    var typeEvaluation: String
)