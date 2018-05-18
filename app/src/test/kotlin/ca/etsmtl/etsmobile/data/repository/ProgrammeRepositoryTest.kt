package ca.etsmtl.etsmobile.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.etsmobile.InstantAppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.ProgrammeDao
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.ListeProgrammes
import ca.etsmtl.etsmobile.data.model.signets.Programme
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.signets.ProgrammeRepository
import ca.etsmtl.etsmobile.util.ApiUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by Sonphil on 17-05-18.
 */
@RunWith(JUnit4::class)
class ProgrammeRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: ProgrammeRepository
    private lateinit var dao: ProgrammeDao
    private val dbData: MutableLiveData<List<Programme>> = MutableLiveData()
    private val listeProgrammes = ListeProgrammes()
    private val signetsModel = SignetsModel<ListeProgrammes>()
    private val userCredentials = SignetsUserCredentials("test", "foo")

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsModel.data = listeProgrammes
        signetsApi = Mockito.mock(SignetsApi::class.java)
        dao = Mockito.mock(ProgrammeDao::class.java)
        Mockito.`when`(dao.getAll()).thenReturn(dbData)
        repo = ProgrammeRepository(InstantAppExecutors(), signetsApi, dao)
    }

    @Test
    fun testLoadFromNetwork() {
        val call: LiveData<ApiResponse<SignetsModel<ListeProgrammes>>> = ApiUtil.successCall(signetsModel)
        Mockito.`when`(signetsApi.listeProgrammes(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<List<Programme>>> = repo.getProgrammes(userCredentials, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned
        data.observeForever(observer as Observer<Resource<List<Programme>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        // Prepare fake updated db data.
        val updatedDbData: MutableLiveData<List<Programme>> = MutableLiveData()
        // Return the fake updated db data when requested
        // After fetching from the network, the data will stored in the db and the db's data will be
        // returned.
        Mockito.`when`(dao.getAll()).thenReturn(updatedDbData)

        // Post null so that the data must be fetched from the network
        dbData.postValue(null)
        // Check if a request was sent to fetch new data
        Mockito.verify(signetsApi).listeProgrammes(userCredentials)
        // After fetching from the network, the data should be inserted into the db.
        Mockito.verify(dao).insertAll(*listeProgrammes.liste.toTypedArray())

        /*
         By now, the updated data has been stored in the db and NetworkBoundResource will get the
         updated data
         */
        updatedDbData.postValue(listeProgrammes.liste)
        Mockito.verify(observer).onChanged(Resource.success(listeProgrammes.liste))
    }

    @Test
    fun testFailToLoadFromNetwork() {
        val errorMsg = "Test error"
        val call: LiveData<ApiResponse<SignetsModel<ListeProgrammes>>> = ApiUtil.failCall(errorMsg)
        Mockito.`when`(signetsApi.listeProgrammes(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<List<Programme>>> = repo.getProgrammes(userCredentials, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned by SignetsApi
        data.observeForever(observer as Observer<Resource<List<Programme>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val fooListProgrammes = ArrayList<Programme>()
        dbData.postValue(fooListProgrammes)
        // Check that a request has been sent to fetch new data
        Mockito.verify(signetsApi).listeProgrammes(userCredentials)
        // The data should not be inserted to the DB since there was an error
        Mockito.verify(dao, Mockito.never()).insertAll(*fooListProgrammes.toTypedArray())
        // Verify that observer has been notified
        Mockito.verify(observer).onChanged(Resource.error(errorMsg, listeProgrammes.liste))
    }

    @Test
    fun testLoadFromDb() {
        val data: LiveData<Resource<List<Programme>>> = repo.getProgrammes(userCredentials, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<List<Programme>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val fooListProgrammes = ArrayList<Programme>()
        dbData.postValue(fooListProgrammes)

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.success(fooListProgrammes))
    }

    @Test
    fun testFailToLoadFromDb() {
        val data: LiveData<Resource<List<Programme>>> = repo.getProgrammes(userCredentials, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<List<Programme>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        dbData.postValue(null)

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.error(NetworkBoundResource.MSG_NO_DATA_DB, null))
    }
}