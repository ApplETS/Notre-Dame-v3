package ca.etsmtl.applets.repository.data.repository

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.db.AppDatabase
import ca.etsmtl.applets.repository.data.repository.signets.LoginRepository
import ca.etsmtl.applets.repository.util.InstantAppExecutors
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import data.db.DashboardCardDatabase
import data.securepreferences.SecurePreferences
import kotlinx.coroutines.runBlocking
import model.SignetsUserCredentials
import model.UniversalCode
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created by Sonphil on 25-04-18.
 */
@RunWith(JUnit4::class)class LoginRepositoryTest {
    private lateinit var prefs: SharedPreferences
    private lateinit var securePrefs: SecurePreferences
    private lateinit var appExecutors: AppExecutors
    private lateinit var appDatabase: AppDatabase
    private lateinit var dashboardCardDatabase: DashboardCardDatabase
    private lateinit var loginRepository: LoginRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        securePrefs = mock(SecurePreferences::class.java)
        prefs = mock(SharedPreferences::class.java)
        appExecutors = InstantAppExecutors()
        appDatabase = mock(AppDatabase::class.java)
        dashboardCardDatabase = mock(DashboardCardDatabase::class.java)
        loginRepository = LoginRepository(
            securePrefs,
            prefs,
            appExecutors,
            appDatabase,
            dashboardCardDatabase
        )
    }

    @Test
    fun testSaveUniversalCode() {
        loginRepository.saveUniversalCode("test")
        verify(securePrefs, times(1)).putString("UniversalCodePref", "test")
    }

    @Test
    fun testGetSavedUniversalCode() {
        val expectedUniversalCode = "AM12345"
        `when`(securePrefs.getString("UniversalCodePref", null)).thenReturn(expectedUniversalCode)
        val universalCode: String = loginRepository.getSavedUniversalCode()!!
        assertEquals(expectedUniversalCode, universalCode)
    }

    @Test
    fun testGetSavedPassword() {
        val expectedPassword = "expectedPw"
        `when`(securePrefs.getString("EncryptedPasswordPref", null)).thenReturn(expectedPassword)
        assertEquals(loginRepository.getSavedPassword(), expectedPassword)
    }

    @Test
    fun testSavePassword() {
        val passwordToSave = "PassowrdToSave"

        loginRepository.savePassword(passwordToSave)
        verify(securePrefs, times(1)).putString("EncryptedPasswordPref", passwordToSave)
    }

    @Test
    fun testClearUserData() {
        val mockSharedPrefsEditor = mock(SharedPreferences.Editor::class.java)
        `when`(prefs.edit()).thenReturn(mockSharedPrefsEditor)
        `when`(mockSharedPrefsEditor.clear()).thenReturn(mockSharedPrefsEditor)
        SignetsUserCredentials.INSTANCE = SignetsUserCredentials(UniversalCode("test"), "test")
        runBlocking {
            loginRepository.clearUserData()

            verify(securePrefs).clear()

            verify(mockSharedPrefsEditor).clear()

            assertNull(SignetsUserCredentials.INSTANCE)

            verify(appDatabase).clearAllTables()

            verify(dashboardCardDatabase).reset()
        }
    }

    @Test
    fun testSaveUserCredentialsIfNeeded() {
        val userCredentials = SignetsUserCredentials(UniversalCode("AM12345"), "test")
        loginRepository.saveUserCredentialsIfNeeded(userCredentials)

        // Check universal code was saved
        verify(securePrefs, times(1)).putString("UniversalCodePref", userCredentials.codeAccesUniversel.value)

        // Check password was saved
        verify(securePrefs, times(1)).putString("EncryptedPasswordPref", userCredentials.motPasse)

        loginRepository.saveUserCredentialsIfNeeded(userCredentials)

        // Make sure there was no interactions since the user's credentials have already been saved
        verifyNoMoreInteractions(securePrefs)
    }
}