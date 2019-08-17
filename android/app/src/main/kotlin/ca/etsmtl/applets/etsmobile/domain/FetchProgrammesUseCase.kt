package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.ProgrammeRepository
import model.Programme
import model.UserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 30-10-18.
 */

class FetchProgrammesUseCase @Inject constructor(
    private val userCredentials: UserCredentials,
    private val repository: ProgrammeRepository
) {
    operator fun invoke(): LiveData<Resource<List<Programme>>> = repository.getProgrammes(userCredentials) { true }
}