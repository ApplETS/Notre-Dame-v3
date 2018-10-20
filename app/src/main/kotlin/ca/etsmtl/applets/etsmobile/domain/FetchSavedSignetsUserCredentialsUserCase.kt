package ca.etsmtl.applets.etsmobile.domain

import ca.etsmtl.applets.repository.data.repository.signets.login.LoginRepository
import javax.inject.Inject

class FetchSavedSignetsUserCredentialsUserCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke() = loginRepository.getSavedUserCredentials()
}