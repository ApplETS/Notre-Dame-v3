package ca.etsmtl.applets.etsmobile.presentation

import android.app.Activity
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.applets.etsmobile.presentation.login.LoginViewModel
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.mockNetwork
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.InfoEtudiantRepository
import ca.etsmtl.applets.repository.data.repository.signets.login.LoginRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 01-05-18.
 */
@RunWith(JUnit4::class)
class LoginViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val infoEtudiantRepository = mock(InfoEtudiantRepository::class.java)
    private val loginRepository: LoginRepository = mock(LoginRepository::class.java)
    private val app: App = mock()
    private val loginViewModel = LoginViewModel(infoEtudiantRepository, loginRepository, app)
    private val userCredentials = SignetsUserCredentials("test", "test")
    @Captor
    private lateinit var userCredentialsArgumentCaptor: ArgumentCaptor<SignetsUserCredentials>
    @Captor
    private lateinit var booleanFunctionArgumentCaptor: ArgumentCaptor<(data: Etudiant?) -> Boolean>
    @Captor
    private lateinit var stringEventArgumentCaptor: ArgumentCaptor<Event<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        app.mockNetwork()
    }

    private fun setAndSubmitCredentials(userCredentials: SignetsUserCredentials) {
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()
    }

    @Test
    fun testCallRepo() {
        val liveData = MutableLiveData<Resource<Etudiant>>()
        `when`(infoEtudiantRepository.getInfoEtudiant(eq(userCredentials), any())).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())
        setAndSubmitCredentials(userCredentials)
        verify(infoEtudiantRepository).getInfoEtudiant(capture(userCredentialsArgumentCaptor), capture(booleanFunctionArgumentCaptor))
        assertEquals(userCredentials, userCredentialsArgumentCaptor.value)
        assertTrue(booleanFunctionArgumentCaptor.value.invoke(null))
    }

    @Test
    fun testNoCallToRepoIfCredentialIsNull() {
        val liveData = MutableLiveData<Resource<Etudiant>>()
        `when`(infoEtudiantRepository.getInfoEtudiant(eq(userCredentials), any())).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())
        loginViewModel.submitCredentials()
        verifyZeroInteractions(infoEtudiantRepository)

        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.submitCredentials()
        verifyZeroInteractions(infoEtudiantRepository)

        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()
        verify(infoEtudiantRepository).getInfoEtudiant(capture(userCredentialsArgumentCaptor), capture(booleanFunctionArgumentCaptor))
        assertEquals(userCredentials, userCredentialsArgumentCaptor.value)
        assertTrue(booleanFunctionArgumentCaptor.value.invoke(null))
    }

    @Test
    fun testErrorMessage() {
        val liveData = MutableLiveData<Resource<Etudiant>>()
        `when`(infoEtudiantRepository.getInfoEtudiant(eq(userCredentials), any())).thenReturn(liveData)

        val message = "Test msg"
        liveData.value = Resource.error(message, null)
        val observer: Observer<Event<String>> = mock()
        loginViewModel.errorMessage.observeForever(observer)
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        verify(observer, never()).onChanged(any())
        loginViewModel.submitCredentials()
        verify(observer).onChanged(capture(stringEventArgumentCaptor))
        assertEquals(message, stringEventArgumentCaptor.value.getContentIfNotHandled())
    }

    @Test
    fun testShowLoading() {
        val liveData = MutableLiveData<Resource<Etudiant>>()
        liveData.value = Resource.loading(null)
        `when`(infoEtudiantRepository.getInfoEtudiant(eq(userCredentials), any())).thenReturn(liveData)

        val observer: Observer<Boolean> = mock()
        loginViewModel.showLoading.observeForever(observer)
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        verify(observer, never()).onChanged(any())
        loginViewModel.submitCredentials()
        verify(observer).onChanged(true)

        liveData.value = Resource.success(Etudiant(
                "fooType",
                "Luu",
                "Phil",
                "LUUP12345678",
                "123,45$",
                true
        ))
        /*
         onSuccess, the UI will navigate to MainActivity and we want the loading animation to
         continue during the transition
          */
        verify(observer, times(2)).onChanged(true)

        liveData.value = Resource.error("foo", null)
        verify(observer, times(1)).onChanged(false)
    }

    @Test
    fun testUniversalCodeError() {
        val emptyError = "Ce champ est requis"
        val invalidError = "Le code universel est invalide"
        `when`(app.getString(R.string.error_field_required)).thenReturn(emptyError)
        `when`(app.getString(R.string.error_invalid_universal_code)).thenReturn(invalidError)

        val observer: Observer<String> = mock()
        loginViewModel.universalCodeError.observeForever(observer)

        var universalCode = ""
        loginViewModel.setUniversalCode(universalCode)
        verify(observer).onChanged(emptyError)

        universalCode = "AZ1234"
        loginViewModel.setUniversalCode(universalCode)
        verify(observer).onChanged(invalidError)

        universalCode = "Z12345"
        loginViewModel.setUniversalCode(universalCode)
        verify(observer, times(2)).onChanged(invalidError)

        universalCode = "AZ12345@ens"
        loginViewModel.setUniversalCode(universalCode)
        verify(observer, times(3)).onChanged(invalidError)

        universalCode = "\\AZ12345"
        loginViewModel.setUniversalCode(universalCode)
        verify(observer, times(4)).onChanged(invalidError)

        universalCode = "AZ12345"
        loginViewModel.setUniversalCode(universalCode)
        verify(observer).onChanged(null)
    }

    @Test
    fun testPasswordError() {
        val emptyError = "Ce champ est requis"
        `when`(app.getString(R.string.error_field_required)).thenReturn(emptyError)

        val observer: Observer<String> = mock()
        loginViewModel.passwordError.observeForever(observer)

        loginViewModel.setPassword("")
        verify(observer).onChanged(emptyError)

        loginViewModel.setPassword("foo")
        verify(observer).onChanged(null)
    }

    @Test
    fun testNavigateToActivity() {
        val observer: Observer<Class<out Activity>> = mock()
        loginViewModel.activityToGoTo.observeForever(observer)

        loginViewModel.clickOnAppletsLogo()
        verify(observer).onChanged(AboutActivity::class.java)

        val liveData = MutableLiveData<Resource<Etudiant>>()
        liveData.value = Resource.loading(null)
        `when`(infoEtudiantRepository.getInfoEtudiant(eq(userCredentials), any())).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())

        setAndSubmitCredentials(userCredentials)
        liveData.value = Resource.error("foo", null)
        verify(observer, never()).onChanged(MainActivity::class.java)
        liveData.value = Resource.success(Etudiant(
                "fooType",
                "Luu",
                "Phil",
                "LUUP12345678",
                "123,45$",
                true
        ))
        verify(observer).onChanged(MainActivity::class.java)
    }

    @Test
    fun testHideKeyboard() {
        val observer: Observer<Void> = mock()
        loginViewModel.hideKeyboard.observeForever(observer)

        loginViewModel.setUniversalCode("AZ12345")
        verify(observer, never()).onChanged(any())

        loginViewModel.setPassword("foo")
        verify(observer, never()).onChanged(any())

        loginViewModel.submitCredentials()
        verify(observer).onChanged(null)
    }

    @Test
    fun showLoginFragment() {
        loginViewModel.showLoading.observeForever(mock())

        val observer: Observer<Void> = mock()
        loginViewModel.showLoginFragment.observeForever(observer)

        val liveData = MutableLiveData<Resource<Etudiant>>()
        `when`(infoEtudiantRepository.getInfoEtudiant(eq(userCredentials), any())).thenReturn(liveData)
        setAndSubmitCredentials(userCredentials)
        liveData.value = Resource.success(Etudiant(
                "fooType",
                "Luu",
                "Phil",
                "LUUP12345678",
                "123,45$",
                true
        ))
        verifyZeroInteractions(observer)

        liveData.value = Resource.error("foo", null)
        verify(observer).onChanged(null)
    }

    @Test
    fun testUniversalCodeInfoButton() {
        val observer: Observer<Boolean> = mock()
        loginViewModel.displayUniversalCodeDialog.observeForever(observer)

        loginViewModel.displayUniversalCodeInfo(true)
        verify(observer).onChanged(true)

        loginViewModel.displayUniversalCodeInfo(false)
        verify(observer).onChanged(false)
    }
}