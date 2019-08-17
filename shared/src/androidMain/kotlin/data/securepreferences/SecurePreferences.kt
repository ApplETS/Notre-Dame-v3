package data.securepreferences

import di.Inject

/**
 * Created by Sonphil on 18-05-19.
 */

actual class SecurePreferences @Inject constructor(){

    private val prefs by lazy {
        PreferencesProvider.preferences()
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        return prefs.getString(key, defaultValue)
    }

    actual fun putString(key: String, value: String) = prefs.putString(key, value)

    actual fun getInt(key: String, defaultValue: Int): Int? = prefs.getInt(key, defaultValue)

    actual fun putInt(key: String, value: Int) = prefs.putInt(key, value)

    actual fun clear() = prefs.clear()
}