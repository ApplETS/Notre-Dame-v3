package data.securepreferences

import kotlinx.cinterop.*
import platform.CoreFoundation.CFDictionaryAddValue
import platform.CoreFoundation.CFDictionaryCreateMutable
import platform.CoreFoundation.CFTypeRefVar
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Security.*

/**
 * Created by Sonphil on 18-05-19.
 */
actual class SecurePreferences {

    private val prefTag:String = "etsmobile_secure_prefs."

    actual fun getString(key: String, defaultValue: String?): String? {
        val itemFound = getData(key) ?: return defaultValue

        return itemFound.getBytes().toString()
    }

    actual fun putString(key: String, value: String) {
        putData(key, value);
    }

    actual fun getInt(key: String, defaultValue: Int): Int? {
        val itemFound = getData(key) ?: return defaultValue

         return itemFound.getBytes().toString().toInt()
    }

    actual fun putInt(key: String, value: Int) {
        putData(key, value)
    }

    actual fun clear() {}

    private fun putData(key: String, value: Any) {
        val prefixedKey:String = prefTag + key

        val query = CFDictionaryCreateMutable(null, 3, null, null)
        // We use generic password to store any type of string
        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword);
        // Adding the key and the value to the dictionary
        CFDictionaryAddValue(query, kSecAttrAccount, CFBridgingRetain(prefixedKey));
        CFDictionaryAddValue(query, kSecValueData, CFBridgingRetain(value));

        // Add the value to the Keychain
        val status = SecItemAdd(query, null);
        CFBridgingRelease(query);
    }

    private fun getData(key: String): CValue<CFTypeRefVar>? {
        val prefixedKey:String = prefTag + key
        val query = CFDictionaryCreateMutable(null, 5, null, null)

        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword)
        // Adding the key and the value to the dictionary
        CFDictionaryAddValue(query, kSecAttrAccount, CFBridgingRetain(prefixedKey))
        CFDictionaryAddValue(query, kSecMatchLimit, CFBridgingRetain(prefixedKey))
        CFDictionaryAddValue(query, kSecAttrAccount, kSecMatchLimitOne)
        CFDictionaryAddValue(query, kSecReturnRef, CFBridgingRetain(true))

        val item = cValue<CFTypeRefVar>()
        val status = SecItemCopyMatching(query, item)

        CFBridgingRelease(query)

        if(status == errSecSuccess)
            return item
        return null
    }
}