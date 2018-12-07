package ca.etsmtl.applets.repository.data.db.entity.signets

import androidx.room.Entity

/**
 * Created by Sonphil on 05-12-18.
 */

@Entity(primaryKeys = ["session", "enseignant", "groupe", "sigle", "typeEvaluation"])
data class EvaluationCoursEntity(
    var session: String,
    var dateDebutEvaluation: Long,
    var dateFinEvaluation: Long,
    var enseignant: String,
    var estComplete: Boolean = false,
    var groupe: String,
    var sigle: String,
    var typeEvaluation: String
)