package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import model.Resource
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
        val remainingCoursesToUpdate = courses.toMutableList()
        val result = MediatorLiveData<Resource<List<Cours>>>()

        if (remainingCoursesToUpdate.isEmpty()) {
            result.value = Resource.success(remainingCoursesToUpdate)
        } else {
            result.value = Resource.loading(remainingCoursesToUpdate)
            remainingCoursesToUpdate.forEach { cours ->
                result.addSource(evaluationRepository.getEvaluationsSummary(
                    userCredentials,
                    cours,
                    true
                )) { res ->
                    if (res == null) {
                        result.value = Resource.error(app.getString(R.string.error), remainingCoursesToUpdate)
                        remainingCoursesToUpdate.clear()
                    } else {
                        if (res.status != Resource.Status.LOADING) {
                            remainingCoursesToUpdate.remove(cours)
                            if (remainingCoursesToUpdate.isEmpty()) {
                                // Dispatch updated courses with last status and message
                                result.value = res.copyStatusAndMessage(remainingCoursesToUpdate)
                            }
                        }
                    }
                }
            }
        }

        return result
    }
}