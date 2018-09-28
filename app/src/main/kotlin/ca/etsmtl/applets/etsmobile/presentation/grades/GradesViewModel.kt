package ca.etsmtl.applets.etsmobile.presentation.grades

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.signets.CoursRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 12-08-18.
 */

class GradesViewModel @Inject constructor(
    private val repository: CoursRepository,
    private var userCredentials: SignetsUserCredentials,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private val coursMediatorLiveData: MediatorLiveData<Resource<List<Cours>>> by lazy {
        MediatorLiveData<Resource<List<Cours>>>()
    }
    private var coursRes: LiveData<Resource<List<Cours>>>? = null
    val errorMessage: LiveData<Event<String?>> by lazy {
        Transformations.map(coursMediatorLiveData) {
            if (it.status == Resource.ERROR) {
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

    fun getCours(): LiveData<Map<String, List<Cours>>> = Transformations.map(coursMediatorLiveData) {
        it.data?.asReversed()?.groupBy {
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
    fun getLoading(): LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        it.status == Resource.LOADING
    }

    /**
     * @return A [LiveData] containing the value true if the empty view should be shown instead of
     * the list
     */
    fun getShowEmptyView(): LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        (it.status != Resource.LOADING && (it?.data == null || it.data?.isEmpty() == true))
    }

    private fun load() {
        coursRes = repository.getCours(userCredentials, true).apply {
            coursMediatorLiveData.addSource(this) {
                coursMediatorLiveData.value = it
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() {
        coursRes?.let { coursMediatorLiveData.removeSource(it) }
        coursRes = null
        load()
    }
}