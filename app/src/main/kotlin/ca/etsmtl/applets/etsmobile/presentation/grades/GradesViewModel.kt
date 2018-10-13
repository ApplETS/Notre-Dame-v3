package ca.etsmtl.applets.etsmobile.presentation.grades

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchGradesCoursesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
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
        Transformations.map(coursMediatorLiveData) {
            if (it.status == Resource.Status.ERROR) {
                when {
                    !app.isDeviceConnected() -> {
                        Event(app.getString(R.string.error_no_internet_connection))
                    }
                    else -> Event(app.getString(R.string.error))
                }
            } else {
                Event(it.message)
            }
        }
    }

    val cours: LiveData<Map<String, List<Cours>>> = Transformations.map(coursMediatorLiveData) {
        it.data
    }

    fun getLoading(): LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        it.status == Resource.Status.LOADING
    }

    /**
     * @return A [LiveData] containing the value true if the empty view should be shown instead of
     * the list
     */
    fun getShowEmptyView(): LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        (it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true))
    }

    private fun load() {
        coursLiveData = fetchGradesCoursesUseCase().apply {
            coursMediatorLiveData.addSource(this) {
                coursMediatorLiveData.value = it
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