package ca.etsmtl.applets.etsmobile.domain

import ca.etsmtl.applets.repository.data.repository.signets.LoginRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 11-10-18.
 */

class ClearUserDataUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke() = loginRepository.clearUserData()
}