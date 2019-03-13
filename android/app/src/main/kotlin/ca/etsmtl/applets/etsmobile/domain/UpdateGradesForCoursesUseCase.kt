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
        val _courses = courses.toMutableList()
        val result = MediatorLiveData<Resource<List<Cours>>>()

        if (_courses.isEmpty()) {
            result.value = Resource.success(_courses)
        } else {
            result.value = Resource.loading(_courses)
            _courses.forEach { cours ->
                result.addSource(evaluationRepository.getEvaluationsSummary(
                    userCredentials,
                    cours,
                    true
                )) { res ->
                    if (res == null) {
                        result.value = Resource.error(app.getString(R.string.error), _courses)
                        _courses.clear()
                    } else {
                        if (res.status != Resource.Status.LOADING) {
                            _courses.remove(cours)
                            if (_courses.isEmpty()) {
                                result.value = Resource.success(_courses)
                            }
                        }
                    }
                }
            }
        }

        return result
    }
}