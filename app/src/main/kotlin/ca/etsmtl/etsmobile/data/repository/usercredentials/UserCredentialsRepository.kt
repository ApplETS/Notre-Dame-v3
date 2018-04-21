package ca.etsmtl.etsmobile.data.repository.usercredentials

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ca.etsmtl.etsmobile.AppExecutors
import ca.etsmtl.etsmobile.data.db.AppDatabase
import ca.etsmtl.etsmobile.data.model.UserCredentials
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

/**
 * Created by Sonphil on 21-04-18.
 */

class UserCredentialsRepository @Inject constructor(
    private val context: Context,
    private val prefs: SharedPreferences,
    private val appExecutors: AppExecutors,
    private val db: AppDatabase
) {
    companion object {
        private const val TAG = "UserCredentialsRepo"
        private const val UNIVERSAL_CODE_PREF = "UniversalCodePref"
        private const val ENCRYPTED_PASSWORD_PREF = "EncryptedPasswordPref"
        /** Key for accessing the key stored in the Keystore **/
        private const val TEST_ALIAS = TAG

        @JvmStatic var userCredentials: AtomicReference<UserCredentials> = AtomicReference()
    }

    /**
     * Saves the user's credentials
     *
     * Saves the [UserCredentials.codeAccesUniversel] to the [SharedPreferences] and the
     * [UserCredentials.motPasse] to the Keystore
     *
     * @param userCredentials The user credentials
     * @return A [LiveData] whose value would be true if the save is complete
     */
    fun saveUserCredentials(userCredentials: UserCredentials): LiveData<Boolean> {
        val finishedBlnLD = MutableLiveData<Boolean>()

        finishedBlnLD.value = false

        appExecutors.diskIO().execute {
            saveUniversalCode(userCredentials.codeAccesUniversel)
            savePassword(userCredentials.motPasse)

            finishedBlnLD.postValue(true)
        }

        return finishedBlnLD
    }

    /**
     * Saves the universal code to the [SharedPreferences]
     *
     * @param universalCode The user's universal code
     */
    private fun saveUniversalCode(universalCode: String) {
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
    fun getSavedUniversalCode(): String? {
        return prefs.getString(UNIVERSAL_CODE_PREF, null)
    }

    /**
     * Gets the password from the Keystore, decrypts it and returns it
     *
     * @return The user's password
     */
    fun getSavedPassword(): String? {
        Log.d(TAG, "Getting password")
        val keyStoreUtils = KeyStoreUtils(context)

        val keyPair = keyStoreUtils.getAndroidKeyStoreAsymmetricKeyPair(TEST_ALIAS)

        val encryptedPW = prefs.getString(ENCRYPTED_PASSWORD_PREF, null)

        val privateKey = keyPair?.private

        return if (privateKey != null) {
            val password = CipherUtils().decrypt(encryptedPW, privateKey)

            Log.d(TAG, "Password successfully decrypted")

            return password
        } else {
            null
        }
    }

    /**
     * Encrypts the password and put it in the Keystore
     *
     * @param password The password to be saved
     */
    private fun savePassword(password: String) {
        val keyStoreUtils = KeyStoreUtils(context)

        keyStoreUtils.createAndroidKeyStoreAsymmetricKey(TEST_ALIAS)

        val keyPair = keyStoreUtils.getAndroidKeyStoreAsymmetricKeyPair(TEST_ALIAS)

        val cipherUtils = CipherUtils()

        keyPair?.let {
            val encryptedPW = cipherUtils.encrypt(password, it.public)

            with(prefs.edit()) {
                putString(ENCRYPTED_PASSWORD_PREF, encryptedPW)
                apply()
            }
        }
    }

    /**
     * Deletes the password saved in the Keystore
     */
    private fun deletePassword() {
        KeyStoreUtils(context).deleteAndroidKeyStoreKeyEntry(TEST_ALIAS)
    }

    /**
     * Clears all the tables that are registered to the database
     *
     * @return A [LiveData] whose the value would be true if the process has finished
     */
    private fun clearDb(): LiveData<Boolean> {
        val clearFinished = MutableLiveData<Boolean>()

        clearFinished.value = false

        appExecutors.diskIO().execute {
            db.clearAllTables()
            clearFinished.postValue(true)
        }

        return clearFinished
    }

    /**
     * Clears the user's data
     *
     * Clears the [SharedPreferences], deletes the password saved in the Keystore and clears the
     * database
     *
     * @return A [LiveData] whose the value would be true if the process has finished
     */
    fun clearUserData(): LiveData<Boolean> {
        UserCredentialsRepository.userCredentials.set(null)

        prefs.edit().clear().apply()

        deletePassword()

        return clearDb()
    }
}