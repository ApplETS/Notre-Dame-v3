package ca.etsmtl.etsmobile.presentation.grades

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.util.Event
import ca.etsmtl.repository.data.model.Cours
import javax.inject.Inject

/**
 * Created by Sonphil on 31-08-18.
 */

class StudentViewModel@Inject constructor(private val app: App) : AndroidViewModel(app), LifecycleObserver {
    private val course by lazy { MutableLiveData<Cours?>() }
    private val title by lazy {
        MediatorLiveData<String>().apply {
            value = app.getString(R.string.title_student)

            addSource(course) {
                this.value = it?.sigle ?: app.getString(R.string.title_student)
            }
        }
    }
    private val subTitle by lazy {
        MediatorLiveData<String>().apply {
            addSource(course) {
                this.value = it?.titreCours
            }
        }
    }

    fun getTitle(): LiveData<String> = title

    fun getSubtitle(): LiveData<String> = subTitle

    val displayCourseGradesDetails: LiveData<Event<Boolean>> by lazy {
        Transformations.map(course) { Event(it != null) }
    }

    fun setCourse(course: Cours) {
        this.course.value = course
    }

    fun getCourse(): Cours? = this.course.value

    /**
     * This function should be called when the user press on the back button. If the evaluation
     * details are currently displayed, the view model will consume the event to hide them.
     *
     * @return True if the event is consumed by the view model or false if not.
     */
    fun back(): Boolean {
        return if (course.value != null) {
            course.value = null
            true
        } else {
            false
        }
    }
}