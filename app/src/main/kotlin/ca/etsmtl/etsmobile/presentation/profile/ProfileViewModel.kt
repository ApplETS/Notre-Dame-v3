package ca.etsmtl.etsmobile.presentation.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Etudiant
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.signets.InfoEtudiantRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class ProfileViewModel @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private var userCredentials: SignetsUserCredentials
) : ViewModel() {

    private var etudiant: LiveData<Resource<Etudiant>>? = null

    fun getInfoEtudiant(): LiveData<Resource<Etudiant>> {
        if (etudiant == null)
            etudiant = repository.getInfoEtudiant(userCredentials, true)

        return etudiant as LiveData<Resource<Etudiant>>
    }
}