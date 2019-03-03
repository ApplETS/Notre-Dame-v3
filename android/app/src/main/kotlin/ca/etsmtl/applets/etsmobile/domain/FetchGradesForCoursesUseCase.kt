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
        return courses.fetchGradesForCoursesThatNeedsIt()
    }

    /**
     * Fetch the grade for each [Cours] that needs it. This method should be called only if
     * the courses have been successfully fetched beforehand.
     */
    private fun List<Cours>.fetchGradesForCoursesThatNeedsIt(): LiveData<Resource<List<Cours>>> {
        val mediatorLiveData = MediatorLiveData<Resource<List<Cours>>>()
        val coursesToFetchGradesFor = filterByCoursesToFetchGradesFor()

        coursesToFetchGradesFor.forEach { cours ->
            mediatorLiveData.addSource(evaluationRepository.getEvaluationsSummary(
                userCredentials,
                cours,
                true
            )) { res ->
                if (res == null) {
                    mediatorLiveData.value = Resource.error(app.getString(R.string.error), this)
                    coursesToFetchGradesFor.clear()
                } else {
                    cours.noteSur100 = res.data?.noteSur100

                    if (res.status == Resource.Status.LOADING) {
                        Resource.loading(this)
                    } else {
                        coursesToFetchGradesFor.remove(cours)

                        if (coursesToFetchGradesFor.isEmpty()) {
                            mediatorLiveData.value = Resource.success(this)
                        }
                    }
                }
            }
        }

        return mediatorLiveData
    }

    @VisibleForTesting
    fun List<Cours>.filterByCoursesToFetchGradesFor() = filter {
        it.hasValidSession() && it.cote.isNullOrBlank() && it.noteSur100.isNullOrBlank()
    }.toMutableList()
}