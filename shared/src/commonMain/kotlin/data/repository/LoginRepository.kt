package data.repository

import data.api.MonETSApi
import data.api.model.MonETSAuthenticationRequestBody
import data.api.model.MonETSUser
import data.db.DashboardCardDatabase
import data.securepreferences.SecurePreferences
import di.Inject
import kotlinx.coroutines.withContext
import model.SignetsUserCredentials
import model.UniversalCode
import utils.EtsMobileDispatchers

class LoginRepository @Inject constructor(
    private val securePrefs: SecurePreferences,
    private val monETSApi: MonETSApi,
    private val dashboardCardDatabase: DashboardCardDatabase
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
            SignetsUserCredentials.INSTANCE =  SignetsUserCredentials(UniversalCode(universalCode), password)
            SignetsUserCredentials.INSTANCE
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

        SignetsUserCredentials.INSTANCE = creds

        monETSUser
    }

    suspend fun clearUserData() {
        withContext(EtsMobileDispatchers.IO) {
            securePrefs.clear()

            SignetsUserCredentials.INSTANCE = null
            
            dashboardCardDatabase.reset()
        }
    }
}