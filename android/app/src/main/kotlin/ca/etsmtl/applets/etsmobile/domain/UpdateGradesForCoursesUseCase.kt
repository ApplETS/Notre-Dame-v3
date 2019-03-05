package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import model.Cours
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 02-03-19.
 */

class UpdateGradesForCoursesUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val evaluationRepository: EvaluationRepository,
    private val app: App
) {
    operator fun invoke(courses: List<Cours>): LiveData<Resource<List<Cours>>> {
        val courses = courses.toMutableList()
        val mediatorLiveData = MediatorLiveData<Resource<List<Cours>>>()

        mediatorLiveData.value = Resource.loading(courses)
        courses.forEach { cours ->
            mediatorLiveData.addSource(evaluationRepository.getEvaluationsSummary(
                userCredentials,
                cours,
                true
            )) { res ->
                if (res == null) {
                    mediatorLiveData.value = Resource.error(app.getString(R.string.error), courses)
                    courses.clear()
                } else {
                    if (res.status != Resource.Status.LOADING) {
                        courses.remove(cours)
                        if (courses.isEmpty()) {
                            mediatorLiveData.value = Resource.success(courses)
                        }
                    }
                }
            }
        }

        return mediatorLiveData
    }
}