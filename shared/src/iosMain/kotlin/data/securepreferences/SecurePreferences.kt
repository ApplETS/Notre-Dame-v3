package data.securepreferences

/**
 * Created by Sonphil on 18-05-19.
 */
// TODO: Use something like native iOS Keychain to implement secure storage methods
actual class SecurePreferences {
    actual fun getString(key: String, defaultValue: String?): String? {
        TODO("not implemented")
    }

    actual fun putString(key: String, value: String) {
        TODO("not implemented")
    }

    actual fun getInt(key: String, defaultValue: Int): Int? {
        TODO("not implemented")
    }

    actual fun putInt(key: String, value: Int) {
        TODO("not implemented")
    }

    actual fun clear() {
        TODO("not implemented")
    }
}