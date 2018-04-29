package ca.etsmtl.etsmobile.presentation.infoetudiant

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class InfoEtudiantViewModel @Inject constructor(
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