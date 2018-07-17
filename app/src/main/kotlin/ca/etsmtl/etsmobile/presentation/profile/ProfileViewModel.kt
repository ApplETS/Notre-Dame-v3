package ca.etsmtl.etsmobile.presentation.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
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
) : ViewModel() {

    private val etudiantMediatorLiveData: MediatorLiveData<Resource<Etudiant>> = MediatorLiveData()
    private var etudiant: LiveData<Resource<Etudiant>>? = null

    fun getInfoEtudiant(): LiveData<Resource<Etudiant>> {
        if (etudiant == null) {
            etudiant = repository.getInfoEtudiant(userCredentials, true)
            etudiant?.let {
                etudiantMediatorLiveData.addSource<Resource<Etudiant>>(it) {
                    etudiantMediatorLiveData.value = it
                }
            }
        }

        return etudiantMediatorLiveData
    }

    fun refresh() {
        etudiant?.let { etudiantMediatorLiveData.removeSource(it) }
        etudiant = null
        getInfoEtudiant()
    }
}