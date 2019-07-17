package data.repository

import data.api.MonETSApi
import data.api.model.MonETSAuthenticationRequestBody
import data.api.model.MonETSUser
import data.securepreferences.SecurePreferences
import di.Inject
import kotlinx.coroutines.withContext
import model.SignetsUserCredentials
import model.UniversalCode
import utils.EtsMobileDispatchers

class LoginRepository @Inject constructor(
    private val securePrefs: SecurePreferences,
    private val monETSApi: MonETSApi
) {
    companion object {
        const val UNIVERSAL_CODE_KEY = "UniversalCodeKey"
        const val PASSWORD_KEY = "PasswordKey"
        const val DOMAINE_KEY = "DomaineKey"
    }

    fun getSavedCredentials(): SignetsUserCredentials? {
        val universalCode = securePrefs.getString(UNIVERSAL_CODE_KEY, null)
        val password = securePrefs.getString(PASSWORD_KEY, null)

        return if (universalCode != null && password != null) {
            SignetsUserCredentials(UniversalCode(universalCode), password)
        } else {
            null
        }
    }

    suspend fun authenticate(creds: SignetsUserCredentials): MonETSUser = withContext(EtsMobileDispatchers.IO) {
        val monETSUser = monETSApi.authenticateUser(MonETSAuthenticationRequestBody(
            creds.motPasse,
            creds.codeAccesUniversel.value
        ))

        securePrefs.putString(UNIVERSAL_CODE_KEY, creds.codeAccesUniversel.value)
        securePrefs.putString(PASSWORD_KEY, creds.motPasse)
        securePrefs.putString(DOMAINE_KEY, monETSUser.domaine)

        monETSUser
    }
}