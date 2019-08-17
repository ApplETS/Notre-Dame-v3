package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.InfoEtudiantRepository
import model.Etudiant
import model.SignetsUserCredentials
import javax.inject.Inject

class FetchEtudiantUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val etudiantRepository: InfoEtudiantRepository
) {
    operator fun invoke(shouldFetch: (data: Etudiant?) -> Boolean): LiveData<Resource<Etudiant>> {
        return etudiantRepository.getInfoEtudiant(userCredentials, shouldFetch)
    }
}