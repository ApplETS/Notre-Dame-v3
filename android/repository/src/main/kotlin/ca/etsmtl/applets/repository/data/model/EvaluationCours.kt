package ca.etsmtl.applets.repository.data.model

import java.util.Date

data class EvaluationCours(
    var session: String,
    var dateDebutEvaluation: Date,
    var dateFinEvaluation: Date,
    var enseignant: String,
    var estComplete: Boolean = false,
    var groupe: String,
    var sigle: String,
    var typeEvaluation: String
)