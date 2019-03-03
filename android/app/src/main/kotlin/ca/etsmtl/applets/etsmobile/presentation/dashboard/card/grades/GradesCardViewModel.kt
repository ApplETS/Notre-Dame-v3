package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.domain.FetchCurrentSessionGradesCoursesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import ca.etsmtl.applets.etsmobile.extension.adjustCote
import ca.etsmtl.applets.repository.data.model.Resource
import model.Cours
import presentation.ViewModel
import javax.inject.Inject

/**
 * Created by Sonphil on 02-03-19.
 */

class GradesCardViewModel @Inject constructor(
    private val fetchCurrentSessionGradesCoursesUseCase: FetchCurrentSessionGradesCoursesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private var _cours = object : RefreshableLiveData<Resource<List<Cours>>>() {
        override fun updateSource(): LiveData<Resource<List<Cours>>> {
            return fetchCurrentSessionGradesCoursesUseCase()
        }
    }
    val cours: LiveData<List<Cours>> = Transformations.map(_cours) { res ->
        res.data?.asReversed()?.apply {
            forEach { course ->
                course.adjustCote(app)
            }
        }
    }

    val errorMessage: LiveData<Event<String?>> = Transformations.map(_cours) {
        Event(it.message)
    }

    val loading: LiveData<Boolean> = Transformations.map(_cours) {
        it?.status == Resource.Status.LOADING
    }

    /**
     * A [LiveData] containing the value true if the empty view should be shown instead of
     * the list
     */
    val showEmptyView: LiveData<Boolean> = Transformations.map(_cours) {
        (it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() = _cours.refresh()
}