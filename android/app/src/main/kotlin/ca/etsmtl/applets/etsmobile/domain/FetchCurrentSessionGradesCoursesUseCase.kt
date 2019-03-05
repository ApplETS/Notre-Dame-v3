package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val fetchCurrentSessionUseCase: FetchCurrentSessionUseCase,
    private val updateGradesForCoursesUseCase: UpdateGradesForCoursesUseCase
) {
    operator fun invoke(): LiveData<Resource<List<Cours>>> {
        return Transformations.switchMap(fetchCurrentSessionUseCase()) { currentSessionRes ->
            Transformations.switchMap(coursRepository.getCours(userCredentials)) { coursesRes ->
                val session = currentSessionRes.data?.abrege
                val courses = coursesRes.data?.filterBySession(session).orEmpty()

                if (coursesRes.status != Resource.Status.SUCCESS) {
                    MutableLiveData<Resource<List<Cours>>>().apply {
                        value = coursesRes.copyStatusAndMessage(coursesRes.data?.filterBySession(session))
                    }
                } else {
                    Transformations.switchMap(updateGradesForCoursesUseCase(courses)) { updateCoursesGradesRes ->
                        Transformations.map(coursRepository.getCours(
                            userCredentials,
                            false
                        )) { updatedCoursesRes ->
                            updatedCoursesRes.copyStatusAndMessage(updatedCoursesRes.data?.filterBySession(session))
                        }
                    }
                }
            }
        }
    }

    private fun List<Cours>.filterBySession(session: String?) = filter { course ->
        course.session == session
    }
}