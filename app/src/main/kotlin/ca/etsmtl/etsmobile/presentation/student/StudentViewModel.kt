package ca.etsmtl.etsmobile.presentation.student

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
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

class StudentViewModel @Inject constructor(private val app: App) : AndroidViewModel(app), LifecycleObserver {
    /** The current course for which details are displayed **/
    private val courseGradesDetails by lazy { MutableLiveData<Cours?>().apply { value = null } }
    val title: LiveData<String> = Transformations.map(courseGradesDetails) {
        it?.sigle ?: app.getString(R.string.title_student)
    }
    val subtitle: LiveData<String> = Transformations.map(courseGradesDetails) { it?.titreCours }
    val displayCourseGradesDetails: LiveData<Event<Boolean>> by lazy {
        Transformations.map(courseGradesDetails) { Event(it != null) }
    }
    val showBackIcon: LiveData<Boolean> = Transformations.map(courseGradesDetails) { it != null }
    val showTabs: LiveData<Boolean> = Transformations.map(courseGradesDetails) { it == null }
    val showBottomNavigationView: LiveData<Boolean> = Transformations.map(courseGradesDetails) {
        it == null
    }

    /**
     * Set the current course for which details are displayed
     *
     * @param course The current course for which details are displayed
     */
    fun setCourseGradesDetails(course: Cours) {
        this.courseGradesDetails.value = course
    }

    /**
     * Returns the current course for which details are displayed
     *
     * @return The current course for which details are displayed
     */
    fun getCourse(): Cours? = this.courseGradesDetails.value

    fun selectTab(position: Int) {
        if (position != StudentPagerAdapter.GRADES) {
            courseGradesDetails.value = null
        }
    }

    /**
     * This function should be called when the user press on the back button. If the evaluation
     * details are currently displayed, the view model will consume the event to hide them.
     *
     * @return True if the event is consumed by the view model or false if not.
     */
    fun back(): Boolean {
        return if (courseGradesDetails.value != null) {
            courseGradesDetails.value = null
            true
        } else {
            false
        }
    }
}