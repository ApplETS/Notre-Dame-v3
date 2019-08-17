package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.CheckUserCredentialsValidUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchSavedSignetsUserCredentialsUserCase
import ca.etsmtl.applets.etsmobile.domain.SaveSignetsUserCredentialsUseCase
import ca.etsmtl.applets.etsmobile.extension.mockNetwork
import ca.etsmtl.applets.etsmobile.presentation.login.LoginViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import model.Resource
import model.SignetsUserCredentials
import model.UniversalCode
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

/**
 * Created by Sonphil on 01-05-18.
 */
@RunWith(JUnit4::class)
class LoginViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fetchSavedSignetsUserCredentialsUserCase = mock(FetchSavedSignetsUserCredentialsUserCase::class.java)
    private val checkUserCredentialsValidUseCase = mock(CheckUserCredentialsValidUseCase::class.java)
    private val savedSignetsUserCredentialsUserCase = mock(SaveSignetsUserCredentialsUseCase::class.java)
    private val app: App = mock()
    private val loginViewModel = LoginViewModel(
            fetchSavedSignetsUserCredentialsUserCase,
            checkUserCredentialsValidUseCase,
            savedSignetsUserCredentialsUserCase,
            app
    )
    private val userCredentials = SignetsUserCredentials(UniversalCode("test"), "test")
    @Captor
    private lateinit var userCredentialsArgumentCaptor: ArgumentCaptor<SignetsUserCredentials>
    @Captor
    private lateinit var stringEventArgumentCaptor: ArgumentCaptor<Event<String>>
    private val liveData = MutableLiveData<Resource<Boolean>>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)

        app.mockNetwork()
    }

    private fun setAndSubmitCredentials(userCredentials: SignetsUserCredentials) {
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()
    }

    @Test
    fun useCase_setAndSubmitCredentials_CalledWithCredentials() {
        // given
        loginViewModel.showLoading.observeForever(mock())

        // when
        setAndSubmitCredentials(userCredentials)

        // then
        verify(checkUserCredentialsValidUseCase).invoke(capture(userCredentialsArgumentCaptor))
        assertEquals(userCredentials, userCredentialsArgumentCaptor.value)
    }

    @Test
    fun useCase_EmptyCredentialsSubmitted_NotCalled() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>()
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())

        // when
        loginViewModel.submitCredentials()

        // then
        verifyZeroInteractions(checkUserCredentialsValidUseCase)
    }

    @Test
    fun useCase_CredentialsSubmittedWithEmptyPassword_NotCalled() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>()
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())

        // when
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.submitCredentials()

        // then
        verifyZeroInteractions(checkUserCredentialsValidUseCase)
    }

    @Test
    fun useCase_CredentialsSubmittedWithNonEmptyFields_Called() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>()
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())

        // when
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()

        // then
        verify(checkUserCredentialsValidUseCase).invoke(capture(userCredentialsArgumentCaptor))
        assertEquals(userCredentials, userCredentialsArgumentCaptor.value)
    }

    @Test
    fun errorMessage_errorOccurredAfterSubmittedCredentials_EmitsErrorMessage() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>()
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        val message = "Test msg"
        liveData.value = Resource.error(message, null)
        val observer: Observer<Event<String>> = mock()
        loginViewModel.errorMessage.observeForever(observer)
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)

        // when
        loginViewModel.submitCredentials()

        // then
        verify(observer).onChanged(capture(stringEventArgumentCaptor))
        assertEquals(message, stringEventArgumentCaptor.value.getContentIfNotHandled())
    }

    @Test
    fun loading_ProcessingCredentials_EmitsTrue() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>().apply {
            value = Resource.loading(null)
        }
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        val observer: Observer<Boolean> = mock()
        loginViewModel.showLoading.observeForever(observer)

        // when
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()

        // then
        verify(observer).onChanged(true)
    }

    @Test
    fun loading_ProcessedCredentialsSuccessfully_EmitsTrue() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>().apply {
            value = Resource.success(true)
        }
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        val observer: Observer<Boolean> = mock()
        loginViewModel.showLoading.observeForever(observer)

        // when
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()

        // then
        /*
         On success, the loading animation should continue. The transition to the next screen looks
         better if the animation doesn't stop.
          */
        verify(observer, times(1)).onChanged(true)
    }

    @Test
    fun loading_ErrorOccurringWhenProcessingCredentials_EmitsFalse() {
        // given
        val liveData = MutableLiveData<Resource<Boolean>>().apply {
            value = Resource.error("foo", null)
        }
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        val observer: Observer<Boolean> = mock()
        loginViewModel.showLoading.observeForever(observer)

        // when
        loginViewModel.setUniversalCode(userCredentials.codeAccesUniversel)
        loginViewModel.setPassword(userCredentials.motPasse)
        loginViewModel.submitCredentials()

        // then
        verify(observer, times(1)).onChanged(false)
    }

    @Test
    fun universalCodeError_InvalidCode_EmitsErrorMessage() {
        // given
        val errorMessage = "Le code universel est invalide"
        `when`(app.getString(R.string.error_invalid_universal_code)).thenReturn(errorMessage)
        val observer: Observer<String> = mock()
        loginViewModel.universalCodeError.observeForever(observer)

        // when
        val invalidUniversalCode = UniversalCode("echasdf")
        loginViewModel.setUniversalCode(invalidUniversalCode)

        // then
        verify(observer).onChanged(errorMessage)
    }

    @Test
    fun universalCode_ValidCode_EmitsNull() {
        // given
        val observer: Observer<String> = mock()
        loginViewModel.universalCodeError.observeForever(observer)

        // when
        val validUniversalCode = UniversalCode("AZ12345")
        loginViewModel.setUniversalCode(validUniversalCode)

        // then
        verify(observer).onChanged(null)
    }

    @Test
    fun passwordError_EmptyPassword_EmitsEmptyErrorMessage() {
        // given
        val emptyError = "Ce champ est requis"
        `when`(app.getString(R.string.error_field_required)).thenReturn(emptyError)
        val observer: Observer<String> = mock()
        loginViewModel.passwordError.observeForever(observer)

        // when
        loginViewModel.setPassword("")

        // then
        verify(observer).onChanged(emptyError)
    }

    @Test
    fun passwordError_NonEmptyPassword_EmitsNull() {
        // given
        val observer: Observer<String> = mock()
        loginViewModel.passwordError.observeForever(observer)

        // when
        loginViewModel.setPassword("foo")

        // then
        verify(observer).onChanged(null)
    }

    @Test
    fun navigateToDashboard_Error_NotCalled() {
        // given
        val observer = mock<EventObserver<Unit>>()
        loginViewModel.navigateToDashboard.observeForever(observer)
        val liveData = MutableLiveData<Resource<Boolean>>()
        liveData.value = Resource.loading(null)
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())
        setAndSubmitCredentials(userCredentials)

        // when
        liveData.value = Resource.error("foo", null)

        // then
        verify(observer, never()).onChanged(any())
    }

    @Test
    fun navigateToDashboard_False_NotCalled() {
        // given
        val observer = mock<EventObserver<Unit>>()
        loginViewModel.navigateToDashboard.observeForever(observer)
        val liveData = MutableLiveData<Resource<Boolean>>()
        liveData.value = Resource.loading(null)
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())
        setAndSubmitCredentials(userCredentials)

        // when
        liveData.value = Resource.success(false)

        // then
        verify(observer, never()).onChanged(any())
    }

    @Test
    fun navigateToDashboard_True_Called() {
        // given
        val observer = mock<EventObserver<Unit>>()
        loginViewModel.navigateToDashboard.observeForever(observer)
        val liveData = MutableLiveData<Resource<Boolean>>()
        liveData.value = Resource.loading(null)
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        loginViewModel.showLoading.observeForever(mock())
        setAndSubmitCredentials(userCredentials)

        // when
        liveData.value = Resource.success(true)

        // then
        verify(observer).onChanged(any())
    }

    @Test
    fun hideKeyboard_UniversalCodeSet_NotCalled() {
        // given
        val observer: Observer<Void> = mock()
        loginViewModel.hideKeyboard.observeForever(observer)

        // when
        loginViewModel.setUniversalCode(UniversalCode("AZ12345"))

        // then
        verify(observer, never()).onChanged(any())
    }

    @Test
    fun hideKeyboard_CredentialsSet_NotCalled() {
        // given
        val observer: Observer<Void> = mock()
        loginViewModel.hideKeyboard.observeForever(observer)

        // when
        loginViewModel.setUniversalCode(UniversalCode("AZ12345"))
        loginViewModel.setPassword("foo")

        // then
        verify(observer, never()).onChanged(any())
    }

    @Test
    fun hideKeyboard_CredentialsSubmitted_Called() {
        // given
        val observer: Observer<Void> = mock()
        loginViewModel.hideKeyboard.observeForever(observer)

        // when
        loginViewModel.setUniversalCode(UniversalCode("AZ12345"))
        loginViewModel.setPassword("foo")
        loginViewModel.submitCredentials()

        // then
        verify(observer).onChanged(null)
    }

    @Test
    fun navigateToLogin_SubmittedCredentialsAreValid_NotCalled() {
        // given
        loginViewModel.showLoading.observeForever(mock())
        val observer: EventObserver<Unit> = mock()
        loginViewModel.navigateToLogin.observeForever(observer)
        val liveData = MutableLiveData<Resource<Boolean>>()
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        setAndSubmitCredentials(userCredentials)

        // when
        liveData.value = Resource.success(true)

        // then
        verifyZeroInteractions(observer)
    }

    @Test
    fun navigateToLogin_SubmittedCredentialsAreMotValid_Called() {
        // given
        loginViewModel.showLoading.observeForever(mock())
        val observer: EventObserver<Unit> = mock()
        loginViewModel.navigateToLogin.observeForever(observer)
        val liveData = MutableLiveData<Resource<Boolean>>()
        `when`(checkUserCredentialsValidUseCase(userCredentials)).thenReturn(liveData)
        setAndSubmitCredentials(userCredentials)

        // when
        liveData.value = Resource.error("foo", false)

        // then
        verify(observer).onChanged(any())
    }

    @Test
    fun displayUniversalCodeDialog_WantsToDisplay_EmitsTrue() {
        // given
        val observer: Observer<Boolean> = mock()
        loginViewModel.displayUniversalCodeDialog.observeForever(observer)

        // when
        loginViewModel.displayUniversalCodeInfo(true)

        // then
        verify(observer).onChanged(true)
    }

    @Test
    fun displayUniversalCodeDialog_WantsToHide_EmitsFalse() {
        // given
        val observer: Observer<Boolean> = mock()
        loginViewModel.displayUniversalCodeDialog.observeForever(observer)

        // when
        loginViewModel.displayUniversalCodeInfo(false)

        // then
        verify(observer).onChanged(false)
    }
}