package ca.etsmtl.repos.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.repos.InstantAppExecutors
import ca.etsmtl.repos.data.api.ApiResponse
import ca.etsmtl.repos.data.api.SignetsApi
import ca.etsmtl.repos.data.db.dao.EtudiantDao
import ca.etsmtl.repos.data.model.Resource
import ca.etsmtl.repos.data.model.signets.Etudiant
import ca.etsmtl.repos.data.model.signets.SignetsModel
import ca.etsmtl.repos.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repos.data.repository.signets.InfoEtudiantRepository
import ca.etsmtl.repos.util.ApiUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class InfoEtudiantRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: InfoEtudiantRepository
    private lateinit var dao: EtudiantDao
    private lateinit var etudiant: Etudiant
    private lateinit var etudiantList: ArrayList<Etudiant>
    private val dbData: MutableLiveData<List<Etudiant>> = MutableLiveData()
    private val userCredentials = SignetsUserCredentials("test", "foo")

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsApi = mock(SignetsApi::class.java)
        dao = mock(EtudiantDao::class.java)
        `when`(dao.getAll()).thenReturn(dbData)
        repo = InfoEtudiantRepository(InstantAppExecutors(), signetsApi, dao)
        etudiantList = ArrayList()
        etudiant = Etudiant("testFoo", "foo", "foo", "foo", "0,00", true, "")
        etudiantList.add(etudiant)
    }

    @Test
    fun testLoadFromNetwork() {
        val signetsModel = SignetsModel<Etudiant>()
        signetsModel.data = etudiant
        val call: LiveData<ApiResponse<SignetsModel<Etudiant>>> = ApiUtil.successCall(signetsModel)
        `when`(signetsApi.infoEtudiant(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<Etudiant>> = repo.getInfoEtudiant(userCredentials, true)
        val observer = mock(Observer::class.java)
        // Start observing the LiveData returned
        data.observeForever(observer as Observer<Resource<Etudiant>>)
        verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        verify(observer).onChanged(Resource.loading(null))

        // Prepare fake updated db data.
        val updatedDbData: MutableLiveData<List<Etudiant>> = MutableLiveData()
        updatedDbData.value = etudiantList
        // Return the fake updated db data when requested
        // After fetching from the network, the data will stored in the db and the db's data will be
        // returned.
        `when`(dao.getAll()).thenReturn(updatedDbData)

        // Post empty list so that the data must be fetched from the network
        dbData.postValue(ArrayList())
        // Check if a request waa sent to fetch new data
        verify(signetsApi).infoEtudiant(userCredentials)
        // After fetching from the network, the data should be inserted into the db.
        verify(dao).insert(etudiant)

        /*
         By now, the updated data has been stored in the db and NetworkBoundResource will get the
         updated data from the db.
         */
        updatedDbData.postValue(etudiantList)
        verify(observer).onChanged(Resource.success(etudiant))
    }

    @Test
    fun testFailToLoadFromNetwork() {
        val signetsModel = SignetsModel<Etudiant>()
        signetsModel.data = etudiant
        val errorMsg = "Test error"
        val call: LiveData<ApiResponse<SignetsModel<Etudiant>>> = ApiUtil.failCall(errorMsg)
        `when`(signetsApi.infoEtudiant(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<Etudiant>> = repo.getInfoEtudiant(userCredentials, true)
        val observer = mock(Observer::class.java)
        // Start observing the LiveData returned
        data.observeForever(observer as Observer<Resource<Etudiant>>)
        verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        verify(observer).onChanged(Resource.loading(null))
        dbData.postValue(etudiantList)
        // Check if a request has been sent to fetch new data
        verify(signetsApi).infoEtudiant(userCredentials)
        // The data should not be inserted to the DB since there was an error
        verify(dao, never()).insert(etudiant)
        // Verify that observer has been notified
        verify(observer).onChanged(Resource.error(errorMsg, etudiant))
    }

    @Test
    fun testLoadFromDb() {
        val data: LiveData<Resource<Etudiant>> = repo.getInfoEtudiant(userCredentials, false)
        val observer = mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<Etudiant>>)
        verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        verify(observer).onChanged(Resource.loading(null))

        dbData.postValue(etudiantList)

        // Make sure there wasn't any interaction with SignetsApi
        verifyNoMoreInteractions(signetsApi)

        verify(observer).onChanged(Resource.success(etudiant))
    }

    @Test
    fun testFailToLoadFromDb() {
        val data: LiveData<Resource<Etudiant>> = repo.getInfoEtudiant(userCredentials, false)
        val observer = mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<Etudiant>>)
        verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        verify(observer).onChanged(Resource.loading(null))

        dbData.postValue(ArrayList())

        // Make sure there wasn't any interaction with SignetsApi
        verifyNoMoreInteractions(signetsApi)

        verify(observer).onChanged(Resource.error(NetworkBoundResource.MSG_NO_DATA_DB, null))
    }
}
