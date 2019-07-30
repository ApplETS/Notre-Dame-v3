package domain

import data.repository.LoginRepository
import di.Inject

/**
 * Created by Sonphil on 19-07-19.
 */

class ClearUserDataUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke() = loginRepository.clearUserData()
}