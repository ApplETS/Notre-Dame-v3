package ca.etsmtl.repository.data.model

/**
 * Created by Sonphil on 14-09-18.
 */

data class SommaireEtEvaluations(
    val sommaireElementsEvaluation: SommaireElementsEvaluation,
    val evaluations: List<Evaluation>
)