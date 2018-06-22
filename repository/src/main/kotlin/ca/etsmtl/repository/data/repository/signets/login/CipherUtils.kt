package ca.etsmtl.repository.data.repository.signets.login

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.inject.Inject

/**
 * Created by Sonphil on 20-04-18.
 */
class CipherUtils @Inject constructor() {
    companion object {
        const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"
    }

    private val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)

    fun encrypt(data: String, key: Key?): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val bytes = cipher.doFinal(data.toByteArray())

        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decrypt(data: String, key: Key?): String {
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encryptedData = Base64.decode(data, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)

        return String(decodedData)
    }
}