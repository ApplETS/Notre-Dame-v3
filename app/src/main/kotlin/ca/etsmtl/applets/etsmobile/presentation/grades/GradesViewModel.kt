package ca.etsmtl.applets.etsmobile.presentation.grades

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.domain.FetchGradesCoursesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.getGenericErrorMessage
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import javax.inject.Inject

/**
 * Created by Sonphil on 12-08-18.
 */

class GradesViewModel @Inject constructor(
    private val fetchGradesCoursesUseCase: FetchGradesCoursesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private val coursMediatorLiveData: MediatorLiveData<Resource<Map<String, List<Cours>>>> by lazy {
        MediatorLiveData<Resource<Map<String, List<Cours>>>>()
    }
    private var coursLiveData: LiveData<Resource<Map<String, List<Cours>>>>? = null
    val errorMessage: LiveData<Event<String?>> by lazy {
        Transformations.map(coursMediatorLiveData) { it.getGenericErrorMessage(app) }
    }

    private val _cours: MutableLiveData<Map<String, List<Cours>>> = MutableLiveData()
    val cours: LiveData<Map<String, List<Cours>>> = _cours

    val loading: LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        it.status == Resource.Status.LOADING
    }

    /**
     * A [LiveData] containing the value true if the empty view should be shown instead of
     * the list
     */
    val showEmptyView: LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        (it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true))
    }

    private fun load() {
        coursLiveData = fetchGradesCoursesUseCase().apply {
            coursMediatorLiveData.addSource(this) {
                coursMediatorLiveData.value = it
                _cours.value = it.data
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() {
        coursLiveData?.let { coursMediatorLiveData.removeSource(it) }
        coursLiveData = null
        load()
    }
}