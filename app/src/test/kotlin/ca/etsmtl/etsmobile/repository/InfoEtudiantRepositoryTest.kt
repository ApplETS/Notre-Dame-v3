package ca.etsmtl.etsmobile.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.EtudiantDao
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.SignetsModel
import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.util.ApiUtil
import ca.etsmtl.etsmobile.util.InstantAppExecutors
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*


/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class InfoEtudiantRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: InfoEtudiantRepository
    private lateinit var dao: EtudiantDao

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsApi = mock(SignetsApi::class.java)
        dao = mock(EtudiantDao::class.java)
        repo = InfoEtudiantRepository(InstantAppExecutors(), signetsApi, dao)
    }

    @Test
    fun testLoadFromNetwork() {
        val dbData: MutableLiveData<Etudiant> = MutableLiveData()
        `when`(dao.getEtudiant()).thenReturn(dbData)

        val etudiant = Etudiant("testFoo", "foo", "foo", "foo", "0,00", true, "")
        val signetsModel = SignetsModel<Etudiant>()
        signetsModel.data = etudiant
        val call : LiveData<ApiResponse<SignetsModel<Etudiant>>> = ApiUtil.successCall(signetsModel)
        val userCredentials = UserCredentials("test", "foo")
        `when`(signetsApi.infoEtudiant(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<Etudiant>> = repo.getInfoEtudiant(userCredentials, true)
        verify(dao).getEtudiant()
        verifyNoMoreInteractions(signetsApi)

        val observer = mock(Observer::class.java)
        data.observeForever(observer as Observer<Resource<Etudiant>>)
        verifyNoMoreInteractions(signetsApi)
        verify(observer).onChanged(Resource.loading(null))
        val updatedDbData: MutableLiveData<Etudiant> = MutableLiveData()
        `when`(dao.getEtudiant()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(signetsApi).infoEtudiant(userCredentials)
        verify(dao).insertEtudiant(etudiant)

        updatedDbData.postValue(etudiant)
        verify(observer).onChanged(Resource.success(etudiant))
    }
}
