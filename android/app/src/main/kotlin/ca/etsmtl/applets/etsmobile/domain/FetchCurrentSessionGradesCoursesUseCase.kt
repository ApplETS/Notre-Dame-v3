package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.data.model.Resource
import model.Cours
import javax.inject.Inject

/**
 * Created by Sonphil on 02-03-19.
 */

class FetchCurrentSessionGradesCoursesUseCase @Inject constructor(
    private val fetchCurrentSessionUseCase: FetchCurrentSessionUseCase,
    private val fetchGradesCoursesUseCase: FetchGradesCoursesUseCase
) {
    operator fun invoke(): LiveData<Resource<List<Cours>>> {
        return Transformations.switchMap(fetchCurrentSessionUseCase()) { currentSessionRes ->
            fetchGradesCoursesUseCase { course ->
                course.session == currentSessionRes.data?.abrege
            }
        }
    }
}