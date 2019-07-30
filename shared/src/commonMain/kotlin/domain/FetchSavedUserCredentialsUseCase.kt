package domain

import data.repository.LoginRepository
import di.Inject

/**
 * Created by Sonphil on 17-07-19.
 */

class FetchSavedUserCredentialsUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke() = loginRepository.getSavedCredentials()
}