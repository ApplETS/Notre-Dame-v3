package ca.etsmtl.etsmobile.presentation.profile

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ca.etsmtl.repository.data.model.Etudiant
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.signets.InfoEtudiantRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class ProfileViewModel @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private var userCredentials: SignetsUserCredentials
) : ViewModel(), LifecycleObserver {

    private val etudiantMediatorLiveData: MediatorLiveData<Resource<Etudiant>> by lazy {
        MediatorLiveData<Resource<Etudiant>>()
    }
    private var etudiantRes: LiveData<Resource<Etudiant>>? = null

    fun getEtudiant(): LiveData<Etudiant> = Transformations.map(etudiantMediatorLiveData) { it.data }
    fun getLoading(): LiveData<Boolean> = Transformations.map(etudiantMediatorLiveData) { it.status == Resource.LOADING }
    fun getErrorMessage(): LiveData<String> = Transformations.map(etudiantMediatorLiveData) {
        it.message
    }

    private fun load() {
        etudiantRes = repository.getInfoEtudiant(userCredentials, true).apply {
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