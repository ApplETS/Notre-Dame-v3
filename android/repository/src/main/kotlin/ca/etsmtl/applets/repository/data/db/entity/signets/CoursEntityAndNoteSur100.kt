package ca.etsmtl.applets.repository.data.db.entity.signets

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Sonphil on 04-03-19.
 */

data class CoursEntityAndNoteSur100(
    @Embedded
    var cours: CoursEntity,
    @Relation(
        parentColumn = "sigle",
        entityColumn = "sigleCours",
        entity = SommaireElementsEvaluationEntity::class,
        projection = ["session", "noteSur100"])
    var sommairesForSigle: List<SommaireElementsEvaluationEntity>
) {
    val noteSur100: String?
        get() = sommairesForSigle.find { it.session == cours.session }?.noteSur100
}