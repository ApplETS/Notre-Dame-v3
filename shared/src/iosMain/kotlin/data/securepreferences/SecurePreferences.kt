package data.securepreferences

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

/**
 * Created by Sonphil on 18-05-19.
 */
actual class SecurePreferences {
    val settings: Settings = AppleSettings.Factory().create("etsmobile_secure_prefs")

    actual fun getString(key: String, defaultValue: String?): String? = settings.getStringOrNull(key)

    actual fun putString(key: String, value: String) = settings.putString(key, value)

    actual fun getInt(key: String, defaultValue: Int): Int? = settings.getIntOrNull(key)

    actual fun putInt(key: String, value: Int) = settings.putInt(key, value)

    actual fun clear() = settings.clear()
}