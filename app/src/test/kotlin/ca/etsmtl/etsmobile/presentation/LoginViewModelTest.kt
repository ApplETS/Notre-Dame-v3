package ca.etsmtl.etsmobile.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.data.repository.login.LoginRepository
import ca.etsmtl.etsmobile.presentation.login.LoginViewModel
import ca.etsmtl.etsmobile.util.mock
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

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

    private fun mockNetwork(connected: Boolean) {
        val connectivityManager = mock(ConnectivityManager::class.java)
        `when`(app.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        val activeNetworkInfo = mock(NetworkInfo::class.java)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(activeNetworkInfo)
        `when`(activeNetworkInfo.isConnected).thenReturn(connected)
    }

    @Test
    fun testGetSavedUserCredentials() {
        loginViewModel.getSavedUserCredentials()
        verify(loginRepository).getSavedUserCredentials()
    }

    @Test
    fun testLDNull() {
        assertThat(loginViewModel.getUserCredentialsIsValid(), notNullValue())
        loginViewModel.setUserCredentials(userCredentials)
        verify(infoEtudiantRepository, never()).getInfoEtudiant(userCredentials, true)
    }

    @Test
    fun testCallRepo() {
        mockNetwork(true)
        val liveData = MutableLiveData<Resource<Etudiant>>()
        `when`(infoEtudiantRepository.getInfoEtudiant(userCredentials, true)).thenReturn(liveData)
        loginViewModel.getUserCredentialsIsValid().observeForever(mock())
        loginViewModel.setUserCredentials(userCredentials)
        verify(infoEtudiantRepository).getInfoEtudiant(userCredentials, true)

        mockNetwork(false)
        val userCredentials2 = SignetsUserCredentials("test2", "test2")
        `when`(infoEtudiantRepository.getInfoEtudiant(userCredentials2, false)).thenReturn(liveData)
        loginViewModel.setUserCredentials(userCredentials2)
        verify(infoEtudiantRepository).getInfoEtudiant(userCredentials2, false)
    }

    @Test
    fun testSendResultToUIValidCredentials() {
        mockNetwork(true)
        val liveData = MutableLiveData<Resource<Etudiant>>()
        val etudiant = Etudiant("testFoo", "foo", "foo", "foo", "0,00", true, "")
        val resource: Resource<Etudiant> = Resource.success(etudiant)
        liveData.value = resource
        `when`(infoEtudiantRepository.getInfoEtudiant(userCredentials, true)).thenReturn(liveData)
        val observer: Observer<Resource<Boolean>> = mock()
        loginViewModel.getUserCredentialsIsValid().observeForever(observer)
        verify(observer, never()).onChanged(any())
        loginViewModel.setUserCredentials(userCredentials)
        val expectedBlnRes: Resource<Boolean> = Resource.success(true)
        verify(observer).onChanged(eq(expectedBlnRes))
    }

    @Test
    fun testSendInvalidResultToUIError() {
        mockNetwork(true)
        val liveData = MutableLiveData<Resource<Etudiant>>()
        val resource: Resource<Etudiant> = Resource.error("foo", null)
        liveData.value = resource
        `when`(infoEtudiantRepository.getInfoEtudiant(userCredentials, true)).thenReturn(liveData)
        val observer: Observer<Resource<Boolean>> = mock()
        loginViewModel.getUserCredentialsIsValid().observeForever(observer)
        verify(observer, never()).onChanged(any())
        loginViewModel.setUserCredentials(userCredentials)
        val expectedBlnRes: Resource<Boolean> = Resource.error("foo", false)
        verify(observer).onChanged(eq(expectedBlnRes))
    }
}