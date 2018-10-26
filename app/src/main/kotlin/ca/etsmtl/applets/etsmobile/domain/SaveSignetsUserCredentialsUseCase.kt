package ca.etsmtl.applets.etsmobile.domain

import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.login.LoginRepository
import javax.inject.Inject

class SaveSignetsUserCredentialsUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(signetsUserCredentials: SignetsUserCredentials) {
        loginRepository.saveUserCredentialsIfNeeded(signetsUserCredentials)
    }
}