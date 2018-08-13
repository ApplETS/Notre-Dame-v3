package ca.etsmtl.etsmobile.presentation.grades

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
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
    private var userCredentials: SignetsUserCredentials
) : ViewModel(), LifecycleObserver {
    private val coursMediatorLiveData: MediatorLiveData<Resource<List<Cours>>> by lazy {
        MediatorLiveData<Resource<List<Cours>>>()
    }
    private var coursRes: LiveData<Resource<List<Cours>>>? = null

    fun getCours(): LiveData<List<Cours>> = Transformations.map(coursMediatorLiveData) { it.data }
    fun getLoading(): LiveData<Boolean> = Transformations.map(coursMediatorLiveData) {
        it.status == Resource.LOADING
    }

    fun getErrorMessage(): LiveData<String> = Transformations.map(coursMediatorLiveData) {
        it.message
    }

    private fun load() {
        coursRes = repository.getCours(userCredentials).apply {
            coursMediatorLiveData.addSource(this) {
                coursMediatorLiveData.value = sortCourses(it)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refresh() {
        coursRes?.let { coursMediatorLiveData.removeSource(it) }
        coursRes = null
        load()
    }

    private fun sortCourses(resCourses: Resource<List<Cours>>?): Resource<List<Cours>>? {
        return when {
            resCourses?.data == null || resCourses.status == Resource.ERROR -> resCourses
            resCourses.status == Resource.LOADING -> Resource.loading(resCourses.data?.sortedByDescending { it.session })
            else -> Resource.success(resCourses.data!!.sortedByDescending { it.session })
        }
    }
}