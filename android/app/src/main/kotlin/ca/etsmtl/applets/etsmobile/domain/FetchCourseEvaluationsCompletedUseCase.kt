package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationCoursRepository
import model.Cours
import model.EvaluationCours
import model.Resource
import model.UserCredentials
import utils.date.ETSMobileDate
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-19.
 */

class FetchCourseEvaluationsCompletedUseCase @Inject constructor(
    private var userCredentials: UserCredentials,
    private val evaluationCoursRepository: EvaluationCoursRepository
) {
    operator fun invoke(cours: Cours): LiveData<Resource<Boolean>> {
        return Transformations.map(evaluationCoursRepository.getEvaluationCours(
            userCredentials,
            cours,
            true
        )) { evaluationCoursRes ->
            val evaluationsCompleted = evaluationCoursRes.data?.areCourseEvaluationsCompletedForCourse(cours) ?: false

            if (evaluationCoursRes.status == Resource.Status.SUCCESS) {
                Resource.success(evaluationsCompleted)
            } else {
                evaluationCoursRes.copyStatusAndMessage(evaluationsCompleted)
            }
        }
    }

    private fun List<EvaluationCours>.areCourseEvaluationsCompletedForCourse(cours: Cours): Boolean {
        return find {
            val duringEvaluationPeriod = ETSMobileDate() in it.dateDebutEvaluation..it.dateFinEvaluation

            !it.estComplete && it.sigle == cours.sigle && duringEvaluationPeriod
        } == null
    }
}