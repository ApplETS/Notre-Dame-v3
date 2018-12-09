package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.EvaluationCours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireEtEvaluations
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationCoursRepository
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 11-10-18.
 */

class FetchGradesDetailsUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val evaluationRepository: EvaluationRepository,
    private val evaluationCoursRepository: EvaluationCoursRepository,
    private val app: App
) {
    operator fun invoke(cours: Cours): LiveData<Resource<SommaireEtEvaluations>> = Transformations.switchMap(evaluationRepository.getSummaryAndEvaluations(
        userCredentials,
        cours,
        true
    )) {
        if (it.status == Resource.Status.ERROR) {
            it.handleError(cours)
        } else {
            MutableLiveData<Resource<SommaireEtEvaluations>>().apply { value = it }
        }
    }

    private fun Resource<SommaireEtEvaluations>.handleError(cours: Cours): LiveData<Resource<SommaireEtEvaluations>> {
        fun List<EvaluationCours>.areCourseEvaluationsCompleted(): Boolean {
            forEach {
                if (!it.estComplete && it.sigle == cours.sigle) {
                    return false
                }
            }

            return true
        }

        if (!app.isDeviceConnected()) {
            return MutableLiveData<Resource<SommaireEtEvaluations>>().apply {
                value = Resource.error(app.getString(R.string.error_no_internet_connection), data)
            }
        } else {
            return Transformations.map(evaluationCoursRepository.getEvaluationCours(
                userCredentials,
                cours,
                true
            )) {
                val genericErrorMsg by lazy { app.getString(R.string.error) }

                if (it.status == Resource.Status.LOADING) {
                    Resource.loading(data)
                } else if (it.status == Resource.Status.ERROR || it.data?.areCourseEvaluationsCompleted() == true) {
                    Resource.error(genericErrorMsg, data)
                } else {
                    Resource.error(app.getString(R.string.error_course_evaluations_not_completed), data)
                }
            }
        }
    }
}