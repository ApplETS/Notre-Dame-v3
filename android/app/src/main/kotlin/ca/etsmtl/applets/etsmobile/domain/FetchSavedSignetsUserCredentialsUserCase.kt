package ca.etsmtl.applets.etsmobile.domain

import ca.etsmtl.applets.repository.data.repository.signets.LoginRepository
import javax.inject.Inject

class FetchSavedSignetsUserCredentialsUserCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke() = loginRepository.getSavedUserCredentials()
}