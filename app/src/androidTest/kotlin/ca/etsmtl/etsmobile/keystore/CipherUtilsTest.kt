package ca.etsmtl.etsmobile.keystore

import ca.etsmtl.etsmobile.data.repository.login.CipherUtils
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.security.KeyPair

/**
 * Created by Sonphil on 22-04-18.
 */
@RunWith(JUnit4::class)
class CipherUtilsTest : KeyStoreCipherTest() {
    companion object {
        private const val SECRET_TXT = "TestToEncrypt"
    }

    private lateinit var keyPair: KeyPair

    @Before
    override fun setUp() {
        super.setUp()

        keyPair = keystoreUtils.createAndroidKeyStoreAsymmetricKey(alias)
    }

    @Test
    fun encryptByUsingKeyGeneratedByKeyStoreTest() {
        val cipherUtils = CipherUtils()

        val encryptedTxt = cipherUtils.encrypt(SECRET_TXT, keyPair.public)

        assertNotNull(encryptedTxt)
        assertTrue(encryptedTxt.length > SECRET_TXT.length)
    }

    @Test
    fun decryptByUsingKeyGeneratedByKeyStoreTest() {
        val cipherUtils = CipherUtils()

        val encryptedTxt = cipherUtils.encrypt(SECRET_TXT, keyPair.public)
        assertNotNull(encryptedTxt)

        val decryptedTxt = cipherUtils.decrypt(encryptedTxt, keyPair.private)

        assertEquals(SECRET_TXT, decryptedTxt)
    }
}