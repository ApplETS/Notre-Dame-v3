package data.repository

import data.api.MonETSApi
import data.api.model.MonETSAuthenticationRequestBody
import data.api.model.MonETSUser
import data.db.DashboardCardDatabase
import data.securepreferences.SecurePreferences
import di.Inject
import kotlinx.coroutines.withContext
import model.UniversalCode
import model.UserCredentials
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

    fun getSavedCredentials(): UserCredentials? {
        val universalCode = securePrefs.getString(UNIVERSAL_CODE_KEY, null)
        val password = securePrefs.getString(PASSWORD_KEY, null)
        val domain = securePrefs.getString(DOMAINE_KEY, null)

        return if (universalCode != null && password != null && domain != null) {
            UserCredentials.INSTANCE = UserCredentials(UniversalCode(universalCode), password, domain)
            UserCredentials.INSTANCE
        } else {
            null
        }
    }

    fun getSavedUserMonETSDomaine(): String? = securePrefs.getString(DOMAINE_KEY, null)

    suspend fun authenticate(universalCode: UniversalCode, password: String): MonETSUser = withContext(EtsMobileDispatchers.IO) {
        val monETSUser = monETSApi.authenticateUser(MonETSAuthenticationRequestBody(
            password,
            universalCode.value
        ))

        securePrefs.putString(UNIVERSAL_CODE_KEY, universalCode.value)
        securePrefs.putString(PASSWORD_KEY, password)
        securePrefs.putString(DOMAINE_KEY, monETSUser.domaine)

        UserCredentials.INSTANCE = UserCredentials(universalCode, password, monETSUser.domaine)

        monETSUser
    }

    suspend fun clearUserData() {
        withContext(EtsMobileDispatchers.IO) {
            securePrefs.clear()

            UserCredentials.INSTANCE = null

            dashboardCardDatabase.reset()
        }
    }
}