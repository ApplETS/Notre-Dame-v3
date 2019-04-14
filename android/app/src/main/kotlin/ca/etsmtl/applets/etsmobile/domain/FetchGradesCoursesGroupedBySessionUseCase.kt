package ca.etsmtl.applets.etsmobile.domain

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import model.Resource
import model.Cours
import javax.inject.Inject

/**
 * Created by Sonphil on 10-10-18.
 */

class FetchGradesCoursesGroupedBySessionUseCase @Inject constructor(
    private val fetchGradesCoursesUseCase: FetchGradesCoursesUseCase,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<Map<String, List<Cours>>>> {
        return fetchGradesCoursesUseCase().mapResCoursesGroupedBySession()
    }

    private fun LiveData<Resource<List<Cours>>>.mapResCoursesGroupedBySession() = Transformations.map(this) {
        val coursesMap = it.getCoursesGroupedBySession()

        when {
            it.status == Resource.Status.LOADING -> Resource.loading(coursesMap)
            it.status == Resource.Status.SUCCESS && coursesMap != null -> Resource.success(coursesMap)
            else -> Resource.error(it.message ?: app.getString(R.string.error), coursesMap)
        }
    }

    /**
     * Groups the [Cours]s of this [Resource] by session by replacing the first letter with the full
     * name
     */
    @VisibleForTesting
    fun Resource<List<Cours>>.getCoursesGroupedBySession() = data
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
        } }