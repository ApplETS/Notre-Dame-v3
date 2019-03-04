package ca.etsmtl.applets.etsmobile.domain

import androidx.annotation.VisibleForTesting
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

class FetchGradesForCoursesUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val evaluationRepository: EvaluationRepository,
    private val app: App
) {
    operator fun invoke(courses: List<Cours>): LiveData<Resource<List<Cours>>> {
        return fetchGradesForCoursesThatNeedsIt(courses)
    }

    /**
     * Fetch the grade for each [Cours] that needs it. This method should be called only if
     * the courses have been successfully fetched beforehand.
     */
    private fun fetchGradesForCoursesThatNeedsIt(courses: List<Cours>): LiveData<Resource<List<Cours>>> {
        val mediatorLiveData = MediatorLiveData<Resource<List<Cours>>>()
        val courses = courses.clone()
        val coursesToFetchGradesFor = courses.filterByCoursesToFetchGradesFor()

        mediatorLiveData.value = Resource.loading(courses.map { it.copy() })
        coursesToFetchGradesFor.forEach { cours ->
            mediatorLiveData.addSource(evaluationRepository.getEvaluationsSummary(
                userCredentials,
                cours,
                true
            )) { res ->
                if (res == null) {
                    mediatorLiveData.value = Resource.error(app.getString(R.string.error), courses)
                    coursesToFetchGradesFor.clear()
                } else {
                    res.data?.let { cours.noteSur100 = it.noteSur100 }

                    if (res.status != Resource.Status.LOADING) {
                        coursesToFetchGradesFor.remove(cours)

                        mediatorLiveData.value = if (coursesToFetchGradesFor.isEmpty()) {
                            Resource.success(courses.clone())
                        } else {
                            Resource.loading(courses.clone())
                        }
                    } else {
                        Resource.loading(courses.clone())
                    }
                }
            }
        }

        return mediatorLiveData
    }

    fun List<Cours>.clone() = map { it.copy() }

    @VisibleForTesting
    fun List<Cours>.filterByCoursesToFetchGradesFor() = filter {
        it.hasValidSession() && it.cote.isNullOrBlank() && it.noteSur100.isNullOrBlank()
    }.toMutableList()
}