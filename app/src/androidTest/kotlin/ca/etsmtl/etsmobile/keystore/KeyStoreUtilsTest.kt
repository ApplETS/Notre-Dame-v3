package ca.etsmtl.etsmobile.keystore

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Sonphil on 22-04-18.
 */

@RunWith(JUnit4::class)
class KeyStoreUtilsTest : KeyStoreCipherTest() {
    @Test
    fun testCreateAndroidKeyStoreAsymmetricKey() {
        val keyPair = keystoreUtils.createAndroidKeyStoreAsymmetricKey(alias)
        assertNotNull(keyPair)
        assertNotNull(keyPair.public)
        assertNotNull(keyPair.private)
    }

    @Test
    fun testGetAndroidKeyStoreAsymmetricKey() {
        assertNull(keystoreUtils.getAndroidKeyStoreAsymmetricKeyPair(alias))
        val keyPair = keystoreUtils.createAndroidKeyStoreAsymmetricKey(alias)
        assertNotNull(keyPair)
        val keyPair2 = keystoreUtils.getAndroidKeyStoreAsymmetricKeyPair(alias)
        assertNotNull(keyPair2)
        assertEquals(keyPair.private, keyPair2!!.private)
        assertEquals(keyPair.public, keyPair2.public)
    }

    @Test
    fun deleteTest() {
        val keyPair = keystoreUtils.createAndroidKeyStoreAsymmetricKey(alias)
        assertNotNull(keyPair)
        keystoreUtils.deleteAndroidKeyStoreKeyEntry(alias)
        assertNull(keystoreUtils.getAndroidKeyStoreAsymmetricKeyPair(alias))
    }
}