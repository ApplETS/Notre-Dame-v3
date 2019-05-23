package data.securepreferences.utils

import android.util.Base64
import di.Inject
import di.Singleton
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Sonphil on 18-05-19.
 */

@Singleton
internal class HashUtils @Inject constructor() {
    /**
     * Hash [String]
     *
     * @param stringToHash String to hash
     * @return SHA-256 Hash of the string or null if it could not be hashed
     */
    fun hashString(stringToHash: String): String? {
        val digest: MessageDigest

        try {
            digest = MessageDigest.getInstance("SHA-256")
            val bytes = stringToHash.toByteArray(charset("UTF-8"))
            digest.update(bytes, 0, bytes.size)

            return Base64.encodeToString(digest.digest(), Base64.NO_WRAP)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return null
    }
}
