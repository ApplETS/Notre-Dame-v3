package ca.etsmtl.applets.repository.data.repository

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.AppDatabase
import ca.etsmtl.applets.repository.data.repository.signets.login.CipherUtils
import ca.etsmtl.applets.repository.data.repository.signets.login.KeyStoreUtils
import ca.etsmtl.applets.repository.data.repository.signets.login.LoginRepository
import ca.etsmtl.applets.repository.util.InstantAppExecutors
import ca.etsmtl.applets.shared.db.DashboardCardQueries
import model.SignetsUserCredentials
import model.UniversalCode
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.verifyZeroInteractions
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 25-04-18.
 */
@RunWith(JUnit4::class)class LoginRepositoryTest {
    private lateinit var keyStoreUtils: KeyStoreUtils
    private lateinit var cipherUtils: CipherUtils
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var appExecutors: AppExecutors
    private lateinit var appDatabase: AppDatabase
    private lateinit var dashboardQueries: DashboardCardQueries
    private lateinit var loginRepository: LoginRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        keyStoreUtils = mock(KeyStoreUtils::class.java)
        cipherUtils = mock(CipherUtils::class.java)
        prefs = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)
        `when`(prefs.edit()).thenReturn(editor)
        appExecutors = InstantAppExecutors()
        appDatabase = mock(AppDatabase::class.java)
        dashboardQueries = mock(DashboardCardQueries::class.java)
        loginRepository = LoginRepository(
            keyStoreUtils,
            cipherUtils,
            prefs,
            appExecutors,
            appDatabase,
            dashboardQueries
        )
    }

    @Test
    fun testSaveUniversalCode() {
        loginRepository.saveUniversalCode("test")
        verify(editor, times(1)).putString("UniversalCodePref", "test")
        verify(editor, atLeastOnce()).apply()
    }

    @Test
    fun testGetSavedUniversalCode() {
        val expectedUniversalCode = "AM12345"
        `when`(prefs.getString("UniversalCodePref", null)).thenReturn(expectedUniversalCode)
        val universalCode: String = loginRepository.getSavedUniversalCode()!!
        assertEquals(expectedUniversalCode, universalCode)
    }

    @Test
    fun testGetSavedPassword() {
        val alias = "TEST_ALIAS"
        val keyPair = mock(KeyPair::class.java)
        val privateKey = mock(PrivateKey::class.java)
        `when`(keyPair.private).thenReturn(privateKey)
        `when`(keyStoreUtils.getAndroidKeyStoreAsymmetricKeyPair(alias)).thenReturn(keyPair)
        `when`(prefs.getString("EncryptedPasswordPref", null)).thenReturn("encryptedPw")
        val expectedPw = "decryptedPW"
        `when`(cipherUtils.decrypt("encryptedPw", privateKey)).thenReturn(expectedPw)

        val decryptedPw = loginRepository.getSavedPassword(alias)
        assertEquals(expectedPw, decryptedPw)
    }

    @Test
    fun testSavePassword() {
        val alias = "TEST_ALIAS"
        val keyPair = mock(KeyPair::class.java)
        val publicKey = mock(PublicKey::class.java)
        `when`(keyPair.public).thenReturn(publicKey)
        `when`(keyStoreUtils.createAndroidKeyStoreAsymmetricKey(alias)).thenReturn(keyPair)
        val passwordToEncrypt = "PwToEncrypt"
        val encryptedPw = "EncryptedPw"
        `when`(cipherUtils.encrypt(passwordToEncrypt, publicKey)).thenReturn(encryptedPw)
        loginRepository.savePassword(passwordToEncrypt, alias)
        verify(editor, times(1)).putString("EncryptedPasswordPref", encryptedPw)
        verify(editor, atLeastOnce()).apply()
    }

    @Test
    fun testDeletePassword() {
        val alias = "TEST_ALIAS"
        loginRepository.deletePassword(alias)
        verify(editor).remove("EncryptedPasswordPref")
        verify(editor).apply()
    }

    @Test
    fun testClearUserData() {
        SignetsUserCredentials.INSTANCE = SignetsUserCredentials(UniversalCode("test"), "test")
        `when`(editor.clear()).thenReturn(editor)
        val finishedLD = loginRepository.clearUserData()
        assertTrue(LiveDataTestUtil.getValue(finishedLD))

        verify(editor).clear()
        verify(editor, times(2)).apply()
        verify(editor).remove("EncryptedPasswordPref")

        assertNull(SignetsUserCredentials.INSTANCE)

        verify(appDatabase).clearAllTables()
        verify(dashboardQueries).deleteAll()
        verify(dashboardQueries).insertInitialCards()
    }

    @Test
    fun testSaveUserCredentialsIfNeeded() {
        val userCredentials = SignetsUserCredentials(UniversalCode("AM12345"), "test")
        val keyPair = mock(KeyPair::class.java)
        val publicKey = mock(PublicKey::class.java)
        `when`(keyPair.public).thenReturn(publicKey)
        `when`(keyStoreUtils.createAndroidKeyStoreAsymmetricKey(userCredentials.codeAccesUniversel.value)).thenReturn(keyPair)
        val encryptedPw = "EncryptedPw"
        `when`(cipherUtils.encrypt(userCredentials.motPasse, publicKey)).thenReturn(encryptedPw)
        loginRepository.saveUserCredentialsIfNeeded(userCredentials)

        // Check universal code was saved
        verify(editor, times(1)).putString("UniversalCodePref", userCredentials.codeAccesUniversel.value)
        verify(editor, atLeastOnce()).apply()

        // Check password was saved
        verify(editor, times(1)).putString("EncryptedPasswordPref", encryptedPw)
        verify(editor, atLeastOnce()).apply()

        loginRepository.saveUserCredentialsIfNeeded(userCredentials)

        // Make sure there was no interactions since the user's credentials have already been saved
        verifyNoMoreInteractions(editor)
        reset(keyStoreUtils)
        verifyZeroInteractions(keyStoreUtils)
        reset(cipherUtils)
        verifyZeroInteractions(cipherUtils)
    }
}