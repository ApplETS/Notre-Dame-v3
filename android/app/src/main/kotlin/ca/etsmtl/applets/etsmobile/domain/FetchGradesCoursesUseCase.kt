package ca.etsmtl.applets.etsmobile.domain

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import model.Cours
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 10-10-18.
 */

class FetchGradesCoursesUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val coursRepository: CoursRepository,
    private val updateGradesForCoursesUseCase: UpdateGradesForCoursesUseCase,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<Map<String, List<Cours>>>> {
        val result = MediatorLiveData<Resource<Map<String, List<Cours>>>>()

        fun sendCachedCoursesAfterGradesUpdate(updateCoursesGradesRes: Resource<List<Cours>>) {
            result.addSource(coursRepository
                .getCours(userCredentials, false)
                .groupCoursesBySession()) { cachedCoursesRes ->
                result.value = if (updateCoursesGradesRes.status == Resource.Status.LOADING) {
                    Resource.loading(cachedCoursesRes.data)
                } else {
                    cachedCoursesRes
                }
            }
        }

        fun sendFetchCoursesError(courses: Resource<List<Cours>>) {
            result.value = Resource.error(
                courses.message ?: app.getString(R.string.error),
                courses.groupCoursesBySession()
            )
        }

        fun updateCoursesAndSendUpdatedCourses(coursesRes: Resource<List<Cours>>) {
            val courses = coursesRes
                .data
                .orEmpty()
                .filterByCoursesToFetchGradesFor()
            val updateCoursesSrc = updateGradesForCoursesUseCase(courses)

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
                result.value = Resource.loading(coursesRes.groupCoursesBySession())
            } else {
                result.removeSource(coursesSrc)

                if (coursesRes.status == Resource.Status.SUCCESS) {
                    updateCoursesAndSendUpdatedCourses(coursesRes)
                } else {
                    sendFetchCoursesError(coursesRes)
                }
            }
        }

        return result
    }

    private fun LiveData<Resource<List<Cours>>>.groupCoursesBySession() = Transformations.map(this) {
        val map = it.groupCoursesBySession()

        when {
            it.status == Resource.Status.LOADING -> Resource.loading(map)
            it.status == Resource.Status.SUCCESS && map != null -> Resource.success(map)
            else -> Resource.error(it.message ?: app.getString(R.string.error), map)
        }
    }

    /**
     * Groups the [Cours] of this [Resource] by session by replacing the first letter with the full
     * name
     */
    @VisibleForTesting
    fun Resource<List<Cours>>.groupCoursesBySession() = data
        ?.asReversed()
        ?.map { it.copy() }
        ?.groupBy {
            it.run {
                when {
                    it.session.startsWith("A") -> it.session.replaceFirst("A", app.getString(R.string.session_fall) + " ")
                    it.session.startsWith("H") -> it.session.replaceFirst("H", app.getString(R.string.session_winter) + " ")
                    it.session.startsWith("É") -> it.session.replaceFirst("É", app.getString(R.string.session_summer) + " ")
                    else -> app.getString(R.string.session_without)
                }
            }
        }

    @VisibleForTesting
    fun List<Cours>.filterByCoursesToFetchGradesFor() = filter {
        it.hasValidSession() && it.cote.isNullOrBlank()
    }.toMutableList()
}