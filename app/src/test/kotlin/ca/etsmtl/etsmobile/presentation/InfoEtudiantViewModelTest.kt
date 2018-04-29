package ca.etsmtl.etsmobile.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.presentation.infoetudiant.InfoEtudiantViewModel
import ca.etsmtl.etsmobile.util.mock
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

class InfoEtudiantViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private var repository: InfoEtudiantRepository = mock(InfoEtudiantRepository::class.java)
    private val userCredentials: SignetsUserCredentials = SignetsUserCredentials("test", "test")
    private val infoEtudiantViewModel = InfoEtudiantViewModel(repository, userCredentials)

    @Test
    fun testCallRepo() {
        val foo = MutableLiveData<Resource<Etudiant>>()
        `when`(repository.getInfoEtudiant(userCredentials, true)).thenReturn(foo)

        infoEtudiantViewModel.getInfoEtudiant().observeForever(ca.etsmtl.etsmobile.util.mock())

        verify(repository).getInfoEtudiant(userCredentials, true)
    }

    @Test
    fun testSendResultToUI() {
        val foo = MutableLiveData<Resource<Etudiant>>()
        `when`(repository.getInfoEtudiant(userCredentials, true)).thenReturn(foo)

        val observer = mock<Observer<Resource<Etudiant>>>()
        verify(observer, Mockito.never()).onChanged(ArgumentMatchers.any())
        infoEtudiantViewModel.getInfoEtudiant().observeForever(observer)
        var fooRes: Resource<Etudiant> = Resource.loading(null)
        foo.value = fooRes
        verify(observer).onChanged(fooRes)

        reset(observer)
        val fooEtudiant = Etudiant("testFoo", "foo", "foo", "foo", "0,00", true, "")
        fooRes = Resource.success(fooEtudiant)
        foo.value = fooRes
        verify(observer).onChanged(fooRes)
    }
}