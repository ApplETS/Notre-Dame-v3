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

    actual fun clear() {
        val query = CFDictionaryCreateMutable(null, 1, null, null)
        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword)

        SecItemDelete(query)
    }

    /**
     * Add some data to the iOS Keychain
     * @param key of the value to store into the keychain
     * @param value to store into the keychain
     */
    private fun putData(key: String, value: Any) {
        val prefixedKey:String = prefTag + key

        val query = CFDictionaryCreateMutable(null, 3, null, null)
        // We use generic password to store any type of string
        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword);
        // Adding the key
        CFDictionaryAddValue(query, kSecAttrAccount, CFBridgingRetain(prefixedKey));

        // Delete any existing ref to this key
        SecItemDelete(query)

        // Adding the value
        CFDictionaryAddValue(query, kSecValueData, CFBridgingRetain(value));

        // Add the value to the Keychain
        val status = SecItemAdd(query, null);
        CFBridgingRelease(query);
    }

    /**
     * @param key of the value to return
     * @return CValue value of corresponding to the param key into the iOS Keychain, null if not found
     */
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