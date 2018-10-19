package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.annotation.VisibleForTesting
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 10-10-18.
 */

class FetchGradesCoursesUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val coursRepository: CoursRepository,
    private val evaluationRepository: EvaluationRepository,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<Map<String, List<Cours>>>> {
        return Transformations.switchMap(coursRepository.getCours(userCredentials, true)) { res ->
            val courses = res.data.orEmpty()
            /** LiveData returned to the caller **/
            val mediatorLiveData = MediatorLiveData<Resource<List<Cours>>>()

            /**
             * Fetch the grade for each [Cours] that needs it. This method should be called only if
             * the courses have been successfully fetched beforehand.
             */
            fun fetchGrades() {
                val coursesToFetchGradesFor = courses.filterByCoursesToFetchGradesFor()

                coursesToFetchGradesFor.forEach { cours ->
                    mediatorLiveData.addSource(evaluationRepository.getEvaluationsSummary(
                            userCredentials,
                            cours,
                            true
                    )) {
                        if (it == null) {
                            mediatorLiveData.value = Resource.error(app.getString(R.string.error), courses)
                            coursesToFetchGradesFor.clear()
                        } else {
                            if (it.status == Resource.Status.LOADING) {
                                Resource.loading(courses)
                            } else {
                                coursesToFetchGradesFor.remove(cours)

                                if (coursesToFetchGradesFor.isEmpty()) {
                                    mediatorLiveData.value = Resource.success(courses)
                                }
                            }
                        }
                    }
                }

                mediatorLiveData.value = when {
                    // No course needs its grade to be fetched
                    coursesToFetchGradesFor.isEmpty() -> Resource.success(courses)
                    // At least one course needs its grade to be fetched
                    else -> Resource.loading(courses)
                }
            }

            when (res.status) {
                Resource.Status.ERROR ->
                    // Return an error to the caller saying that an error occurred during the fetch
                    mediatorLiveData.value = Resource.error(res.message ?: app.getString(R.string.error), courses)
                Resource.Status.SUCCESS ->
                    /*
                    The courses have been successfully fetched. The grades of some courses must be
                    fetched.
                     */
                    fetchGrades()
                Resource.Status.LOADING -> mediatorLiveData.value = Resource.loading(courses)
            }

            /*
             Group the courses by session because the UI would like to display the courses no matter
             what
              */
            mediatorLiveData.groupBySession()
        }
    }

    /**
     * Filters the courses that we need to fetch the grade for
     */
    @VisibleForTesting
    fun List<Cours>.filterByCoursesToFetchGradesFor() = filter {
        it.hasValidSession() && it.cote.isNullOrBlank() && it.noteSur100.isNullOrBlank()
    }.toMutableList()

    private fun LiveData<Resource<List<Cours>>>.groupBySession() = Transformations.map(this) {
        val map = it.groupBySession()

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
    fun Resource<List<Cours>>.groupBySession() = data?.asReversed()?.groupBy {
        it.run {
            when {
                it.session.startsWith("A") -> it.session.replaceFirst("A", app.getString(R.string.session_fall) + " ")
                it.session.startsWith("H") -> it.session.replaceFirst("H", app.getString(R.string.session_winter) + " ")
                it.session.startsWith("É") -> it.session.replaceFirst("É", app.getString(R.string.session_summer) + " ")
                else -> app.getString(R.string.session_without)
            }
        }
    }
}