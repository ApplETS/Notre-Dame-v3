package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.domain.FetchEtudiantUseCase
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileViewModel
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify

/**
 * Created by Sonphil on 28-04-18.
 */

class ProfileViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fetchEtudiantUseCase: FetchEtudiantUseCase = mock(FetchEtudiantUseCase::class.java)
    private val profileViewModel = ProfileViewModel(fetchEtudiantUseCase)

    @Test
    fun testCallUseCase() {
        val foo = MutableLiveData<Resource<Etudiant>>()
        `when`(fetchEtudiantUseCase(any())).thenReturn(foo)

        profileViewModel.refresh()

        verify(fetchEtudiantUseCase).invoke(any())
    }

    @Test
    fun testSendResultToUI() {
        val foo = MutableLiveData<Resource<Etudiant>>()
        `when`(fetchEtudiantUseCase(any())).thenReturn(foo)

        val etudiantObserver = mock<Observer<Etudiant>>()
        verify(etudiantObserver, Mockito.never()).onChanged(ArgumentMatchers.any())
        val loadingObserver = mock<Observer<Boolean>>()
        verify(loadingObserver, Mockito.never()).onChanged(ArgumentMatchers.any())
        val errorMsgObserver = mock<Observer<String>>()
        verify(errorMsgObserver, Mockito.never()).onChanged(ArgumentMatchers.any())

        profileViewModel.etudiant.observeForever(etudiantObserver)
        profileViewModel.loading.observeForever(loadingObserver)
        profileViewModel.errorMessage.observeForever(errorMsgObserver)

        profileViewModel.refresh()

        var fooRes: Resource<Etudiant> = Resource.loading(null)
        foo.value = fooRes
        verify(etudiantObserver).onChanged(null)
        verify(loadingObserver).onChanged(true)
        verify(errorMsgObserver).onChanged(null)

        reset(etudiantObserver)
        reset(loadingObserver)
        reset(errorMsgObserver)

        val fooEtudiant = Etudiant("testFoo", "foo", "foo", "foo", "0,00", true)
        fooRes = Resource.success(fooEtudiant)
        foo.value = fooRes
        verify(etudiantObserver).onChanged(fooEtudiant)
        verify(loadingObserver).onChanged(false)
        verify(errorMsgObserver).onChanged(null)

        reset(etudiantObserver)
        reset(loadingObserver)
        reset(errorMsgObserver)

        val errorMsg = "Test error"
        fooRes = Resource.error(errorMsg, null)
        foo.value = fooRes
        verify(etudiantObserver).onChanged(null)
        verify(loadingObserver).onChanged(false)
        verify(errorMsgObserver).onChanged(errorMsg)
    }
}