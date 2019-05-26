package data.securepreferences

/**
 * Created by Sonphil on 18-05-19.
 */

expect class SecurePreferences {
    fun getString(key: String, defaultValue: String?): String?

    fun putString(key: String, value: String)

    fun getInt(key: String, defaultValue: Int): Int?

    fun putInt(key: String, value: Int)

    fun clear()
}
