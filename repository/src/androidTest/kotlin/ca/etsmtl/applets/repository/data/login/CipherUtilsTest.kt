package ca.etsmtl.applets.repository.data.login

import android.os.Build
import android.util.Base64
import ca.etsmtl.applets.repository.data.repository.signets.login.CipherUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
        // RSA key size: [KeyStoreUtils.RSA_KEY_SIZE] (2048 bits)
        private const val PRIVATE_KEY_CONTENT = "MIIEogIBAAKCAQB09PyoKBu+mGdQKKhq53CABfMg0CGWXr93k6AhiNypW6Br8Xji rZ6kYT4Vt9I0BeAKlVbDluIbPd2BHlkw0rhAWEjrkCWMyc7PatKPfm84JMZctIJT qkJyxn/UQYp9XuCU6h81Isd0GoZ5Bf6qYASrxbd2NRxFghmi7Sab1gd4GmeZAg08 q11RxoqrKclWd5JHWDonNd85aELjy7VXhoIBVIqbvCFoZU7pAJwKJqu6pPNPKP4i c4oFP1knqcZBTsn+PrnUHhHGLhrscGZq4CExjLDffbLJaYnn1cQ+3cscISgcsXUS HZzCLiN0sAh9OpIWnG8UlpHi2yAsGAVVLMe7AgMBAAECggEAPh1WiiAzC/h49hOB zrT4BKYkR8sB65CKc4FNzEGZn8Bv5KQsJYc79+VFSdeEpehw4m4J1HqvvvQOTeLG T625+UJqB+FYb/tGANak2Vy2yMSPLPEZPkHwZHCTtzDP0mGvOKdWx6ue7kSgImpS pD22fVvWYcs3Jps/7k2w8ZasBNaZDS/D1n1L7AG/K7FIxZwBaE9F22l30AISF2Bj 5JWs9pYw3ZcVaSxx7F4PuN9H7kzUj9rAK41WNFcD5YICe7H1q5uPy3NCWGB7aWZ6 h0IPlq91+ew0Z+nEgMVlSxXjJUHRO+gHD7eXt9/jmNdF1yaM/47Fb0SHu+HhWtPZ KhS0YQKBgQC3hs0CoSs355yC7aBxZpV03D2GpZ/4IbcmPTbqPPxCrweVqtqVO5Yn 1NmaV9t4Ul0dom1PwXG0zd7iZce30lp5pi2iYabmUai8l/41dPHuNh3SrVvXRxY7 PBljFg8Rmuny6DexpQe8BhG7oW32qTYLJqZPNKayNLcYSZgZKNnISQKBgQCjJHxg v3kgXAt/g6JY5r4Y2x3kXj38cNYBDwE2VUudcxS5XdqY1qr48Rzt9p1lHapTw8Yy 3uKa6Gdrh5ou0AIlYQQi6d/ZLygfZqDpPHJGZN4Oiau32dWmh8U4RsctOILffcCK kswgI6iF8jdqHXoiINC9I+Xg0xhqWVk4ZsS34wKBgQCvlESQbna1fl1LGWH0++qi qY4nQk9ACal56Pifsoa4mVFNS+7g9Ofb5ZPlSIaMn2e8UUf5FJ3fF1JUEIG5CINM fMKN5pTRJnCjNVqb8+z9NELl6rrBw52uP/odxN3XvvhxTt7xkmxK4xeWBYP5hrtQ gcJ8ZmfQeARvNQFRmLZ6GQKBgDK7eeIsKOKjPQAeUzIKLztTiGwn0mbaP/J8VAgt ZiJEU0GBqiS1PN3FPRXTBwcWvozl6JCRNy6s7txFgvDjxocRMFQbHn1PaM66/3cI UREK9PLYZQn7N7Fdrzrc57tsQ2Z0mJd/JvMno5Rjyk+5cu2B/ueSbj4H5yPLPS+I 5cu5AoGAc7JXt1ETgA5JGTsX8GqCjABSV/pV6Ys7BE54fHqcJVKK9CGepfSAO24b CLmclVhHS217F01kfpG58HTyTHte/If7dtXbj+VUOodGpQQj46EzjyAtVBXB1lb0 m5acz5PJ0BBEM6Joq4OizFiSpp/dZvEG4kCEwwcAOdIvCaMZlls="
        private const val PUBLIC_KEY_CONTENT = "MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQB09PyoKBu+mGdQKKhq53CA BfMg0CGWXr93k6AhiNypW6Br8XjirZ6kYT4Vt9I0BeAKlVbDluIbPd2BHlkw0rhA WEjrkCWMyc7PatKPfm84JMZctIJTqkJyxn/UQYp9XuCU6h81Isd0GoZ5Bf6qYASr xbd2NRxFghmi7Sab1gd4GmeZAg08q11RxoqrKclWd5JHWDonNd85aELjy7VXhoIB VIqbvCFoZU7pAJwKJqu6pPNPKP4ic4oFP1knqcZBTsn+PrnUHhHGLhrscGZq4CEx jLDffbLJaYnn1cQ+3cscISgcsXUSHZzCLiN0sAh9OpIWnG8UlpHi2yAsGAVVLMe7 AgMBAAE="
        private const val ENCRYPTED_TXT = "Mg3rMph1yRxrAufjMjnyCjv9AEAOyf503S5569E4deO55+QDsIw0p+2NKVbmW7FSdDFAAOQ0CTRPCeuNJkneObhQvLiaaHrMBeKOwHytmXL8iJ9NE6XyEL/m/JowWVgrRe1DIAycmwj9x2ms8pkmP6pHcqbrHimVI6J4RIvcRPeHaKZUUnv96FNm2kh4F/uJyQ3Y3KN/GyKYc4XdT34mk/1L9HUNgHIkKzRK6mJw00m3x9KfHvYgNYsgin6tKmjv8Kxqc0+bFFkUEvn4Auh550Pc/sub7ryHSUsWXtN2T0cvrjayEyqVo2RBYNywKSWAYk8XgwALOQHHMXXkWrkGLg=="
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
        assertEquals(
                ENCRYPTED_TXT.length,
                encryptedTxt.trim().replace("\n", "").length
        )

        val privateKey = convertStringToPrivateKey(PRIVATE_KEY_CONTENT)
        val decryptedText = cipherUtils.decrypt(encryptedTxt, privateKey)
        assertEquals(decryptedText, SECRET_TXT)
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
        assertEquals(
                ENCRYPTED_TXT.length,
                encryptedTxt.trim().replace("\n", "").length
        )

        val decryptedText = cipherUtils.decrypt(encryptedTxt, keyPair.private)
        assertEquals(decryptedText, SECRET_TXT)
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
        val keyFactory = KeyFactory.getInstance("RSA")

        return keyFactory.generatePublic(keySpec)
    }

    private fun convertStringToPrivateKey(privateKeyContent: String): PrivateKey {
        val clear = Base64.decode(privateKeyContent, Base64.DEFAULT)
        val keySpec = PKCS8EncodedKeySpec(clear)
        val fact = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            KeyFactory.getInstance("RSA", "BC")
        } else {
            KeyFactory.getInstance("RSA")
        }
        val privateKey = fact.generatePrivate(keySpec)
        Arrays.fill(clear, 0.toByte())
        return privateKey
    }
}