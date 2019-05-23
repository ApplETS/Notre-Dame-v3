package ca.etsmtl.applets.repository.data.repository.signets.login

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.db.AppDatabase
import ca.etsmtl.applets.shared.db.DashboardCardQueries
import data.securepreferences.utils.CipherUtils
import data.securepreferences.utils.KeyStoreUtils
import model.SignetsUserCredentials
import model.UniversalCode
import javax.inject.Inject

/**
 * Created by Sonphil on 21-04-18.
 */

class LoginRepository @Inject constructor(
    private val keyStoreUtils: KeyStoreUtils,
    private val cipherUtils: CipherUtils,
    private val prefs: SharedPreferences,
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
            savePassword(userCredentials.motPasse, userCredentials.codeAccesUniversel.value)

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
            val motPasse = getSavedPassword(codeAccesUniversel)

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
        with(prefs.edit()) {
            putString(UNIVERSAL_CODE_PREF, universalCode)
            apply()
        }
    }

    /**
     * Returns the universal code from the [SharedPreferences]
     *
     * @return The user's universal code
     */
    @VisibleForTesting
    fun getSavedUniversalCode(): String? {
        return prefs.getString(UNIVERSAL_CODE_PREF, null)
    }

    /**
     * Gets the password from the Keystore, decrypts it and returns it
     *
     * @param alias Key for accessing the key stored in the Keystore
     * @return The user's password
     */
    @VisibleForTesting
    fun getSavedPassword(alias: String): String? {
        val keyPair = keyStoreUtils.getAndroidKeyStoreAsymmetricKeyPair(alias)

        val encryptedPW = prefs.getString(ENCRYPTED_PASSWORD_PREF, null)

        val privateKey = keyPair?.private

        return if (privateKey != null) {
            return cipherUtils.decrypt(encryptedPW, privateKey)
        } else {
            null
        }
    }

    /**
     * Encrypts the password and put it in the [SharedPreferences]
     *
     * @param password The password to be saved
     * @param alias Key that would be used to for accessing the key stored in the Keystore
     */
    @VisibleForTesting
    fun savePassword(password: String, alias: String) {
        val keyPair = keyStoreUtils.createAndroidKeyStoreAsymmetricKey(alias)

        keyPair.let {
            val encryptedPW = cipherUtils.encrypt(password, it.public)

            with(prefs.edit()) {
                putString(ENCRYPTED_PASSWORD_PREF, encryptedPW)
                apply()
            }
        }
    }

    /**
     * Deletes the saved password
     *
     * @param alias Key for accessing the key stored in the Keystore
     */
    @VisibleForTesting
    fun deletePassword(alias: String) {
        keyStoreUtils.deleteAndroidKeyStoreKeyEntry(alias)
        with(prefs.edit()) {
            remove(ENCRYPTED_PASSWORD_PREF)
            apply()
        }
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
            prefs.edit().clear().apply()

            with(SignetsUserCredentials.INSTANCE) {
                if (this != null) {
                    deletePassword(this.codeAccesUniversel.value)

                    SignetsUserCredentials.INSTANCE = null
                }
            }

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