package ca.etsmtl.repos.data.login

import android.util.Base64
import ca.etsmtl.repos.data.repository.signets.login.CipherUtils
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.security.KeyFactory
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Arrays

/**
 * Created by Sonphil on 22-04-18.
 */
@RunWith(JUnit4::class)
class CipherUtilsTest : KeyStoreCipherTest() {
    companion object {
        private const val SECRET_TXT = "TextToEncrypt"
        private const val PUBLIC_KEY_CONTENT = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZuuuyTTdW/" +
                "nla6ytplwsNh1xQilOye9GeTyh1+9zSS4xZRbtpWbHY3wV10n80n6+TyV7+1UxbN7KB6Nn48eimoF93V" +
                "UC4ndAXQd5PcW5KV70Paeea8QL858Zr9ov6lkCtgfznmoMs/wzqhq1PSGjjkMafH2nZressfMwPzMKxm" +
                "QIDAQAB"
        private const val PRIVATE_KEY_CONTENT = "MIICXQIBAAKBgQDZuuuyTTdW/nla6ytplwsNh1xQilOye9Ge" +
                "Tyh1+9zSS4xZRbtpWbHY3wV10n80n6+TyV7+1UxbN7KB6Nn48eimoF93VUC4ndAXQd5PcW5KV70Paeea" +
                "8QL858Zr9ov6lkCtgfznmoMs/wzqhq1PSGjjkMafH2nZressfMwPzMKxmQIDAQABAoGAEjS3EKm0Pun4" +
                "a8nOT335xp/S8EkgxoPj+0DTOUpeJJd8nNpf2WdOn7vGURvQ1y2jGNB1yQk84UO/AW3fDBnuQhkY8OW2" +
                "NBkuJtaXxwpI9K6xmkafdvA00Oczhlw7wKuV+UTlYIk4IUaOcfpt6O7uFmPYsKqpQLZLD87u1bKPZoEC" +
                "QQDxLtWNh8IKf3QUdIkefnRbHxTjlYNUE8oECVsH1y1HWflpshlJKifTJM3Zr2T8bP2Ecn/HHaR0OMzA" +
                "6PmvOz3lAkEA5xs8kHQdGQCo73CcvpG0vels348fegC2QJavxp6F9UVm+VYjN+dKvsrvycYSznV70hIi" +
                "7PMatlgFktdI4F/JpQJAR2PslIs11bOqYY+pMtyNhpdpsIAGMrH030MBrg10eqVYXX/5Wh3fUfGX+TKg" +
                "utNsK3NU/VR55GkXupZ7qfHoPQJBAIioGWMN1hrvwfV8MKObgzX9bqyvdBxviVWeBSi8mlfThzWPEto+" +
                "wXsagzpcDsjYvbQVl2NnyXbji8At32EWIyUCQQCOsBtTMYvQnMPz5825mfXp/Sj9Jxi5pzba3PcsExiS" +
                "4fj+xH8jPiVaCdgf3+FP7AxqUuFZhq89N9flcE4iUMBG"
        private const val ENCRYPTED_TXT = "dHtQIrXATqMMiWqhPmIaMNnPQS2cP5WJauFXCKTccAhP3Xwpat1G+Z" +
                "P+eOyK6+YdmOjbsy+4LSeVmS+9XVoQdjHdZ3RJAYX4c8YUiHgJ4dkhimEgXXvrcyE/3ecGW0R4YZXcUZ" +
                "fjm3JwfhyDjBJZp5id7+6wp1+pPP7+ivWu4Q8="
    }

    private lateinit var keyPair: KeyPair
    private lateinit var cipherUtils: CipherUtils

    @Before
    override fun setUp() {
        super.setUp()

        keyPair = keystoreUtils.createAndroidKeyStoreAsymmetricKey(alias)
        cipherUtils = CipherUtils()
    }

    @Test
    fun encrypt() {
        val publicKey = convertStringToPublicKey(PUBLIC_KEY_CONTENT)

        val encryptedTxt = cipherUtils.encrypt(SECRET_TXT, publicKey)
        assertNotNull(encryptedTxt.length > SECRET_TXT.length)
    }

    @Test
    fun decryptTest() {
        val privateKey = convertStringToPrivateKey(PRIVATE_KEY_CONTENT)

        val decryptedTxt = cipherUtils.decrypt(ENCRYPTED_TXT, privateKey)
        assertEquals(SECRET_TXT, decryptedTxt)
    }

    @Test
    fun encryptByUsingKeyGeneratedByKeyStoreTest() {
        val encryptedTxt = cipherUtils.encrypt(SECRET_TXT, keyPair.public)

        assertNotNull(encryptedTxt)
        assertTrue(encryptedTxt.length > SECRET_TXT.length)
    }

    @Test
    fun decryptByUsingKeyGeneratedByKeyStoreTest() {
        val encryptedTxt = cipherUtils.encrypt(SECRET_TXT, keyPair.public)
        assertNotNull(encryptedTxt)

        val decryptedTxt = cipherUtils.decrypt(encryptedTxt, keyPair.private)

        assertEquals(SECRET_TXT, decryptedTxt)
    }

    private fun convertStringToPublicKey(publicKeyContent: String): PublicKey {
        val publicBytes = Base64.decode(publicKeyContent, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory = KeyFactory.getInstance("RSA", "BC")

        return keyFactory.generatePublic(keySpec)
    }

    private fun convertStringToPrivateKey(privateKeyContent: String): PrivateKey {
        val clear = Base64.decode(privateKeyContent, Base64.DEFAULT)
        val keySpec = PKCS8EncodedKeySpec(clear)
        val fact = KeyFactory.getInstance("RSA", "BC")
        val privateKey = fact.generatePrivate(keySpec)
        Arrays.fill(clear, 0.toByte())
        return privateKey
    }
}