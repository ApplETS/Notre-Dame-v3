package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.isDeviceConnected
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import ca.etsmtl.applets.repository.util.replaceCommaAndParseToDouble
import model.Cours
import model.Resource
import model.SignetsUserCredentials
import model.SommaireEtEvaluations
import javax.inject.Inject

/**
 * Created by Sonphil on 11-10-18.
 */

class FetchGradesDetailsUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val evaluationRepository: EvaluationRepository,
    private val fetchCourseEvaluationsCompletedUseCase: FetchCourseEvaluationsCompletedUseCase,
    private val app: App
) {
    operator fun invoke(cours: Cours): LiveData<Resource<SommaireEtEvaluations>> = Transformations.switchMap(evaluationRepository.getSummaryAndEvaluations(
        userCredentials,
        cours,
        true
    )) { sommaireEtEvaluationsRes ->
        if (sommaireEtEvaluationsRes.status == Resource.Status.ERROR) {
            sommaireEtEvaluationsRes.handleError()
        } else if (sommaireEtEvaluationsRes.shouldCheckForEvaluationsCompletion()) {
            handleEvaluationsMightBeIncomplete(sommaireEtEvaluationsRes, cours)
        } else {
            MutableLiveData<Resource<SommaireEtEvaluations>>().apply {
                value = sommaireEtEvaluationsRes
            }
        }
    }

    private fun Resource<SommaireEtEvaluations>.handleError(): LiveData<Resource<SommaireEtEvaluations>> {
        val result = MutableLiveData<Resource<SommaireEtEvaluations>>()

        result.value = if (!app.isDeviceConnected()) {
            Resource.error(app.getString(R.string.error_no_internet_connection), data)
        } else {
            Resource.error(message ?: app.getString(R.string.error), data)
        }

        return result
    }

    private fun Resource<SommaireEtEvaluations>.shouldCheckForEvaluationsCompletion(): Boolean {
        val noteStr = data?.sommaireElementsEvaluation?.note

        return status == Resource.Status.SUCCESS && (noteStr == null || noteStr.replaceCommaAndParseToDouble() == 0.0)
    }

    private fun handleEvaluationsMightBeIncomplete(
        sommaireEtEvaluationsRes: Resource<SommaireEtEvaluations>,
        cours: Cours
    ): LiveData<Resource<SommaireEtEvaluations>> {
        return Transformations.map(fetchCourseEvaluationsCompletedUseCase(cours)) { evaluationsCompletedRes ->
            if (evaluationsCompletedRes.status == Resource.Status.SUCCESS) {
                if (evaluationsCompletedRes.data == true) {
                    sommaireEtEvaluationsRes
                } else {
                    Resource.error<SommaireEtEvaluations>(
                        app.getString(R.string.error_course_evaluations_not_completed),
                        null
                    )
                }
            } else {
                evaluationsCompletedRes.copyStatusAndMessage(sommaireEtEvaluationsRes.data)
            }
        }
    }
}