package ca.etsmtl.etsmobile.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.etsmobile.InstantAppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.SessionDao
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.ListeDeSessions
import ca.etsmtl.etsmobile.data.model.signets.Session
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.signets.SessionRepository
import ca.etsmtl.etsmobile.util.ApiUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by Sonphil on 26-05-18.
 */
@RunWith(JUnit4::class)
class SessionRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: SessionRepository
    private lateinit var dao: SessionDao
    private val dbData: MutableLiveData<List<Session>> = MutableLiveData()
    private val listeSessions = ListeDeSessions()
    private val signetsModel = SignetsModel<ListeDeSessions>()
    private val userCredentials = SignetsUserCredentials("test", "foo")

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsModel.data = listeSessions
        signetsApi = Mockito.mock(SignetsApi::class.java)
        dao = Mockito.mock(SessionDao::class.java)
        Mockito.`when`(dao.getAll()).thenReturn(dbData)
        repo = SessionRepository(InstantAppExecutors(), signetsApi, dao)
    }

    @Test
    fun testLoadFromNetwork() {
        val call: LiveData<ApiResponse<SignetsModel<ListeDeSessions>>> = ApiUtil.successCall(signetsModel)
        Mockito.`when`(signetsApi.listeSessions(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<List<Session>>> = repo.getSessions(userCredentials, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned
        data.observeForever(observer as Observer<Resource<List<Session>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        // Prepare fake updated db data.
        val updatedDbData: MutableLiveData<List<Session>> = MutableLiveData()
        // Return the fake updated db data when requested
        // After fetching from the network, the data will stored in the db and the db's data will be
        // returned.
        Mockito.`when`(dao.getAll()).thenReturn(updatedDbData)

        // Post null so that the data must be fetched from the network
        dbData.postValue(null)
        // Check if a request was sent to fetch new data
        Mockito.verify(signetsApi).listeSessions(userCredentials)
        // After fetching from the network, the data should be inserted into the db.
        Mockito.verify(dao).insertAll(*listeSessions.liste.toTypedArray())

        /*
         By now, the updated data has been stored in the db and NetworkBoundResource will get the
         updated data
         */
        updatedDbData.postValue(listeSessions.liste)
        Mockito.verify(observer).onChanged(Resource.success(listeSessions.liste))
    }

    @Test
    fun testFailToLoadFromNetwork() {
        val errorMsg = "Test error"
        val call: LiveData<ApiResponse<SignetsModel<ListeDeSessions>>> = ApiUtil.failCall(errorMsg)
        Mockito.`when`(signetsApi.listeSessions(userCredentials)).thenReturn(call)

        val data: LiveData<Resource<List<Session>>> = repo.getSessions(userCredentials, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned by SignetsApi
        data.observeForever(observer as Observer<Resource<List<Session>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val fooListSessions = ArrayList<Session>()
        dbData.postValue(fooListSessions)
        // Check that a request has been sent to fetch new data
        Mockito.verify(signetsApi).listeSessions(userCredentials)
        // The data should not be inserted to the DB since there was an error
        Mockito.verify(dao, Mockito.never()).insertAll(*fooListSessions.toTypedArray())
        // Verify that observer has been notified
        Mockito.verify(observer).onChanged(Resource.error(errorMsg, listeSessions.liste))
    }

    @Test
    fun testLoadFromDb() {
        val data: LiveData<Resource<List<Session>>> = repo.getSessions(userCredentials, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<List<Session>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val fooListSessions = ArrayList<Session>()
        dbData.postValue(fooListSessions)

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.success(fooListSessions))
    }

    @Test
    fun testFailToLoadFromDb() {
        val data: LiveData<Resource<List<Session>>> = repo.getSessions(userCredentials, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<List<Session>>>)
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