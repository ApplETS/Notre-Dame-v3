package ca.etsmtl.applets.repository.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.AppDatabase
import ca.etsmtl.applets.repository.data.repository.signets.LoginRepository
import ca.etsmtl.applets.repository.util.InstantAppExecutors
import ca.etsmtl.applets.shared.db.DashboardCardQueries
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import data.securepreferences.SecurePreferences
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
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 25-04-18.
 */
@RunWith(JUnit4::class)class LoginRepositoryTest {
    private lateinit var prefs: SecurePreferences
    private lateinit var appExecutors: AppExecutors
    private lateinit var appDatabase: AppDatabase
    private lateinit var dashboardQueries: DashboardCardQueries
    private lateinit var loginRepository: LoginRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        prefs = mock(SecurePreferences::class.java)
        appExecutors = InstantAppExecutors()
        appDatabase = mock(AppDatabase::class.java)
        dashboardQueries = mock(DashboardCardQueries::class.java)
        loginRepository = LoginRepository(
            prefs,
            appExecutors,
            appDatabase,
            dashboardQueries
        )
    }

    @Test
    fun testSaveUniversalCode() {
        loginRepository.saveUniversalCode("test")
        verify(prefs, times(1)).putString("UniversalCodePref", "test")
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
        val expectedPassword = "expectedPw"
        `when`(prefs.getString("EncryptedPasswordPref", null)).thenReturn(expectedPassword)
        assertEquals(loginRepository.getSavedPassword(), expectedPassword)
    }

    @Test
    fun testSavePassword() {
        val passwordToSave = "PassowrdToSave"

        loginRepository.savePassword(passwordToSave)
        verify(prefs, times(1)).putString("EncryptedPasswordPref", passwordToSave)
    }

    @Test
    fun testClearUserData() {
        SignetsUserCredentials.INSTANCE = SignetsUserCredentials(UniversalCode("test"), "test")
        val finishedLD = loginRepository.clearUserData()
        assertTrue(LiveDataTestUtil.getValue(finishedLD))

        verify(prefs).clear()

        assertNull(SignetsUserCredentials.INSTANCE)

        verify(appDatabase).clearAllTables()
        verify(dashboardQueries).deleteAll()
        verify(dashboardQueries).insertInitialCards()
    }

    @Test
    fun testSaveUserCredentialsIfNeeded() {
        val userCredentials = SignetsUserCredentials(UniversalCode("AM12345"), "test")
        loginRepository.saveUserCredentialsIfNeeded(userCredentials)

        // Check universal code was saved
        verify(prefs, times(1)).putString("UniversalCodePref", userCredentials.codeAccesUniversel.value)

        // Check password was saved
        verify(prefs, times(1)).putString("EncryptedPasswordPref", userCredentials.motPasse)

        loginRepository.saveUserCredentialsIfNeeded(userCredentials)

        // Make sure there was no interactions since the user's credentials have already been saved
        verifyNoMoreInteractions(prefs)
    }
}