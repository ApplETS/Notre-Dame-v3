package data.securepreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import data.securepreferences.utils.CipherUtils
import data.securepreferences.utils.KeyStoreUtils
import data.securepreferences.utils.hash
import java.security.KeyPair

/**
 * Created by Sonphil on 18-05-19.
 */

internal class SharedPreferencesWrapper constructor(
    private val prefs: SharedPreferences,
    private val cipherUtils: CipherUtils,
    private val keyStoreUtils: KeyStoreUtils
) {
    private fun getKeyPair(prefKey: String): KeyPair {
        return keyStoreUtils.getAndroidKeyStoreAsymmetricKeyPair(prefKey)
            ?: keyStoreUtils.createAndroidKeyStoreAsymmetricKey(prefKey)
    }

    fun getString(key: String, defaultValue: String?): String? {
        val encrypted = prefs.getString(key.hash(), null)

        return if (encrypted != null) {
            val keyPair = getKeyPair(key)

            cipherUtils.decrypt(encrypted, keyPair.private)
        } else {
            defaultValue
        }
    }

    fun putString(key: String, value: String) {
        val keyPair = getKeyPair(key)
        val encrypted = cipherUtils.encrypt(value, keyPair.public)

        prefs.edit {
            putString(key.hash(), encrypted)
        }
    }

    fun getInt(key: String, defaultValue: Int): Int? {
        return prefs.getString(key.hash(), null)?.toInt() ?: defaultValue
    }

    fun putInt(key: String, value: Int) {
        prefs.edit {
            val valueStr = value.toString()
            val keyPair = getKeyPair(key)
            val encrypted = cipherUtils.encrypt(valueStr, keyPair.public)

            putString(key.hash(), encrypted)
        }
    }

    fun clear() {
        prefs.all.forEach {
            keyStoreUtils.deleteAndroidKeyStoreKeyEntry(it.key)
        }

        prefs.edit().clear().commit()
    }
}