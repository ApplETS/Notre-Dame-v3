package ca.etsmtl.applets.etsmobile.domain

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import model.Cours
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 06-03-19.
 */

class FetchGradesCoursesUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val coursRepository: CoursRepository,
    private val updateGradesForCoursesUseCase: UpdateGradesForCoursesUseCase,
    private val app: App
) {
    operator fun invoke(coursesFilterPredicate: (Cours) -> Boolean = { true }): LiveData<Resource<List<Cours>>> {
        val result = MediatorLiveData<Resource<List<Cours>>>()

        fun Resource<List<Cours>>.coursesFiltered() = data?.filter(coursesFilterPredicate)

        fun sendCachedCoursesAfterGradesUpdate(updateCoursesGradesRes: Resource<List<Cours>>) {
            result.addSource(coursRepository
                .getCours(userCredentials, false)) { cachedCoursesRes ->
                result.value = if (updateCoursesGradesRes.status == Resource.Status.LOADING) {
                    Resource.loading(cachedCoursesRes.coursesFiltered())
                } else {
                    cachedCoursesRes.copyStatusAndMessage(cachedCoursesRes.coursesFiltered())
                }
            }
        }

        fun sendFetchCoursesError(courses: Resource<List<Cours>>) {
            result.value = Resource.error(
                courses.message ?: app.getString(R.string.error),
                courses.coursesFiltered()
            )
        }

        fun updateCoursesGradesAndSendUpdatedCourses(coursesRes: Resource<List<Cours>>) {
            val coursesToFetchGradesFor = coursesRes
                .data
                .orEmpty()
                .filterByCoursesToFetchGradesFor()
            val updateCoursesSrc = updateGradesForCoursesUseCase(coursesToFetchGradesFor)

            result.addSource(updateCoursesSrc) { updateCoursesGradesRes ->

                if (updateCoursesGradesRes.status != Resource.Status.LOADING) {
                    result.removeSource(updateCoursesSrc)
                }

                sendCachedCoursesAfterGradesUpdate(updateCoursesGradesRes)
            }
        }

        val coursesSrc = coursRepository.getCours(userCredentials)

        result.value = Resource.loading(null)
        result.addSource(coursesSrc) { coursesRes ->
            if (coursesRes.status == Resource.Status.LOADING) {
                result.value = Resource.loading(coursesRes.coursesFiltered())
            } else {
                result.removeSource(coursesSrc)

                if (coursesRes.status == Resource.Status.SUCCESS) {
                    updateCoursesGradesAndSendUpdatedCourses(coursesRes)
                } else {
                    sendFetchCoursesError(coursesRes)
                }
            }
        }

        return result
    }

    @VisibleForTesting
    fun List<Cours>.filterByCoursesToFetchGradesFor() = filter {
        it.hasValidSession() && it.cote.isNullOrBlank()
    }.toMutableList()
}