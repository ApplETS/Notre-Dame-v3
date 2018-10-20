package ca.etsmtl.applets.etsmobile.domain

import android.arch.lifecycle.LiveData
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.InfoEtudiantRepository
import javax.inject.Inject

class FetchEtudiantUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val etudiantRepository: InfoEtudiantRepository
) {
    operator fun invoke(shouldFetch: (data: Etudiant?) -> Boolean): LiveData<Resource<Etudiant>> {
        return etudiantRepository.getInfoEtudiant(userCredentials, shouldFetch)
    }
}