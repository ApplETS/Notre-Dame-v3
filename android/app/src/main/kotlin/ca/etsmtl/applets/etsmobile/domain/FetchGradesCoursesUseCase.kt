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
    private val fetchGradesForCourses: FetchGradesForCourses,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<Map<String, List<Cours>>>> {
        return Transformations.switchMap(coursRepository.getCours(userCredentials, true)) { res ->
            val courses = res.data.orEmpty()
            /** LiveData returned to the caller **/
            val mediatorLiveData = MediatorLiveData<Resource<List<Cours>>>()

            if (res.status == Resource.Status.ERROR) {
                // Return an error to the caller saying that an error occurred during the fetch
                mediatorLiveData.value = Resource.error(res.message ?: app.getString(R.string.error), courses)
            } else {
                if (res.status == Resource.Status.LOADING) {
                    mediatorLiveData.value = Resource.loading(courses)
                }

                mediatorLiveData.addSource(fetchGradesForCourses(courses)) {
                    mediatorLiveData.value = it
                }
            }

            mediatorLiveData.groupBySession()
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
}