package ca.etsmtl.applets.etsmobile.presentation.profile

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.domain.FetchEtudiantUseCase
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class ProfileViewModel @Inject constructor(
    private val fetchEtudiantUseCase: FetchEtudiantUseCase
) : ViewModel(), LifecycleObserver {

    private val etudiantMediatorLiveData: MediatorLiveData<Resource<Etudiant>> by lazy {
        MediatorLiveData<Resource<Etudiant>>()
    }
    private var etudiantRes: LiveData<Resource<Etudiant>>? = null

    val etudiant: LiveData<Etudiant> = Transformations.map(etudiantMediatorLiveData) { it.data }
    val loading: LiveData<Boolean> = Transformations.map(etudiantMediatorLiveData) { it.status == Resource.Status.LOADING }
    val errorMessage: LiveData<String> = Transformations.map(etudiantMediatorLiveData) {
        it.message
    }

    private fun load() {
        etudiantRes = fetchEtudiantUseCase { true }.apply {
            etudiantMediatorLiveData.addSource<Resource<Etudiant>>(this) {
                etudiantMediatorLiveData.value = it
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refresh() {
        etudiantRes?.let { etudiantMediatorLiveData.removeSource(it) }
        etudiantRes = null
        load()
    }
}