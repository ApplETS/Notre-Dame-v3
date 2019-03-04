package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import model.Cours
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 02-03-19.
 */

class FetchCurrentSessionGradesCoursesUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val coursRepository: CoursRepository,
    private val fetchGradesForCoursesUseCase: FetchGradesForCoursesUseCase,
    private val fetchCurrentSessionUseCase: FetchCurrentSessionUseCase
) {
    operator fun invoke(): LiveData<Resource<List<Cours>>> {
        return Transformations.switchMap(fetchCurrentSessionUseCase()) { currentSessionRes ->
            val coursesSrc = coursRepository.getCours(userCredentials)

            Transformations.switchMap(coursesSrc) { coursesRes ->
                val courses = coursesRes.data?.filter { course ->
                    course.session == currentSessionRes.data?.abrege
                }.orEmpty()

                fetchGradesForCoursesUseCase(courses)
            }
        }
    }
}