package ca.etsmtl.applets.etsmobile.domain

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        return Transformations.switchMap(coursRepository.getCours(userCredentials)) { coursesRes ->
            if (coursesRes.status != Resource.Status.SUCCESS) {
                MutableLiveData<Resource<Map<String, List<Cours>>>>().apply {
                    value = coursesRes.copyStatusAndMessage(coursesRes.groupBySession())
                }
            } else {
                val courses = coursesRes.data.orEmpty().filterByCoursesToFetchGradesFor()

                Transformations.switchMap(updateGradesForCoursesUseCase(courses)) { updateCoursesGradesRes ->
                    coursRepository.getCours(userCredentials, false).groupBySession()
                }
            }
        }
    }

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

    @VisibleForTesting
    fun List<Cours>.filterByCoursesToFetchGradesFor() = filter {
        it.hasValidSession() && it.cote.isNullOrBlank() && it.noteSur100.isNullOrBlank()
    }.toMutableList()
}