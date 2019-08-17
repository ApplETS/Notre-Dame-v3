package model

import com.soywiz.klock.DateTime

data class EvaluationCours(
    var session: String,
    var dateDebutEvaluation: DateTime,
    var dateFinEvaluation: DateTime,
    var enseignant: String,
    var estComplete: Boolean = false,
    var groupe: String,
    var sigle: String,
    var typeEvaluation: String
)