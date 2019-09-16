package ca.etsmtl.applets.repository.data.repository.signets

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.db.AppDatabase
import ca.etsmtl.applets.shared.db.DashboardCardQueries
import data.securepreferences.SecurePreferences
import model.SignetsUserCredentials
import model.UniversalCode
import javax.inject.Inject

/**
 * Created by Sonphil on 21-04-18.
 */

class LoginRepository @Inject constructor(
    private val securePrefs: SecurePreferences,
    private val appExecutors: AppExecutors,
    private val db: AppDatabase,
    private val dashboardCardQueries: DashboardCardQueries
) {
    companion object {
        private const val TAG = "LoginRepository"
        private const val UNIVERSAL_CODE_PREF = "UniversalCodePref"
        private const val ENCRYPTED_PASSWORD_PREF = "EncryptedPasswordPref"
    }

    /**
     * Saves the user's credentials
     *
     * Saves the [SignetsUserCredentials.codeAccesUniversel] to the [SharedPreferences] and the
     * [SignetsUserCredentials.motPasse] to the [SharedPreferences] after encrypting it
     *
     * @param userCredentials The user's credentials
     * @return A [LiveData] whose value would be true if the save is complete
     */
    private fun saveUserCredentials(userCredentials: SignetsUserCredentials): LiveData<Boolean> {
        val finishedBlnLD = MutableLiveData<Boolean>()

        finishedBlnLD.value = false

        appExecutors.diskIO().execute {
            saveUniversalCode(userCredentials.codeAccesUniversel.value)
            savePassword(userCredentials.motPasse)

            finishedBlnLD.postValue(true)
        }

        return finishedBlnLD
    }

    /**
     * Saves the user's credentials if they haven't been saved before
     *
     * Saves the [SignetsUserCredentials.codeAccesUniversel] to the [SharedPreferences] and the
     * [SignetsUserCredentials.motPasse] to the [SharedPreferences] after encrypting it
     *
     * @param userCredentials The user's credentials
     * @return A [LiveData] whose value would be true if the save is complete
     */
    fun saveUserCredentialsIfNeeded(userCredentials: SignetsUserCredentials): LiveData<Boolean> {
        return if (SignetsUserCredentials.INSTANCE == null) {
            SignetsUserCredentials.INSTANCE = userCredentials

            saveUserCredentials(userCredentials)
        } else {
            MutableLiveData<Boolean>().apply { value = true }
        }
    }

    /**
     * Returns the [SignetsUserCredentials.codeAccesUniversel] and the [SignetsUserCredentials.motPasse]
     *
     * @return The saved [SignetsUserCredentials]
     */
    fun getSavedUserCredentials(): SignetsUserCredentials? {
        val codeAccesUniversel = getSavedUniversalCode()

        var userCredentials: SignetsUserCredentials? = null

        if (codeAccesUniversel != null) {
            val motPasse = getSavedPassword()

            if (motPasse != null) {
                userCredentials = SignetsUserCredentials(UniversalCode(codeAccesUniversel), motPasse)
                SignetsUserCredentials.INSTANCE = userCredentials
            }
        }

        return userCredentials
    }

    /**
     * Saves the universal code to the [SharedPreferences]
     *
     * @param universalCode The user's universal code
     */
    @VisibleForTesting
    fun saveUniversalCode(universalCode: String) {
        securePrefs.putString(UNIVERSAL_CODE_PREF, universalCode)
    }

    /**
     * Returns the universal code from the [SharedPreferences]
     *
     * @return The user's universal code
     */
    @VisibleForTesting
    fun getSavedUniversalCode(): String? {
        return securePrefs.getString(UNIVERSAL_CODE_PREF, null)
    }

    /**
     * Gets the password from the Keystore, decrypts it and returns it
     *
     * @return The user's password
     */
    @VisibleForTesting
    fun getSavedPassword(): String? {
        return securePrefs.getString(ENCRYPTED_PASSWORD_PREF, null)
    }

    /**
     * Encrypts the password and put it in the [SharedPreferences]
     *
     * @param password The password to be saved
     */
    @VisibleForTesting
    fun savePassword(password: String) {
        securePrefs.putString(ENCRYPTED_PASSWORD_PREF, password)
    }

    /**
     * Clears the user's data
     *
     * Clears the [SharedPreferences], deletes the saved password and clears the
     * database
     *
     * @return A [LiveData] with a value set to true if the process has finished or false if the
     * process hasn't finished yet
     */
    fun clearUserData(): LiveData<Boolean> {
        val clearFinished = MutableLiveData<Boolean>()
        clearFinished.value = false

        // Add clearing task to diskIO queue
        // Wait for operations running on diskIO thread to finish
        // Prevent crash when the user when logout while the password is being saved
        appExecutors.diskIO().execute {
            securePrefs.clear()

            SignetsUserCredentials.INSTANCE = null

            db.clearAllTables()

            resetDashboard()

            clearFinished.postValue(true)
        }

        return clearFinished
    }

    private fun resetDashboard() = with(dashboardCardQueries) {
        deleteAll()
        insertInitialCards()
    }
}