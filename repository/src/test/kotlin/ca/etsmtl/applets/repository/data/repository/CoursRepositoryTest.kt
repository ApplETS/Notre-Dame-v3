package ca.etsmtl.applets.repository.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.response.signets.ApiCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toCours
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import ca.etsmtl.applets.repository.util.ApiUtil
import ca.etsmtl.applets.repository.util.InstantAppExecutors
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.eq
import junit.framework.Assert.assertEquals
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Sonphil on 10-08-18.
 */
@RunWith(JUnit4::class)
class CoursRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: CoursRepository
    private lateinit var dao: CoursDao
    private lateinit var evaluationRepository: EvaluationRepository
    private val dbData: MutableLiveData<List<CoursEntity>> = MutableLiveData()
    private val apiCours1 = ApiCours(
            "LOG530",
            "01",
            "H2018",
            "7365",
            "A",
            3,
            "Réingénierie du logiciel"
    )
    private val apiCours2 = ApiCours(
            "LOG710",
            "01",
            "H2018",
            "7365",
            "",
            3,
            "Principes systèmes d'expl. et programmation système"
    )
    private val signetsModel = ApiSignetsModel<ApiListeDeCours>().apply {
        data = ApiListeDeCours(
                "SignetsPourEtudiants.SignetsMobile+ListeDeCours",
                ArrayList<ApiCours>().apply {
                    add(apiCours1)
                    add(apiCours2)
                },
                ""
        )
    }
    private val userCredentials = SignetsUserCredentials("test", "foo")
    @Captor
    private lateinit var coursEntitiesArgumentCaptor: ArgumentCaptor<List<CoursEntity>>

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        signetsApi = mock(SignetsApi::class.java)
        dao = mock(CoursDao::class.java)
        `when`(dao.getAll()).thenReturn(dbData)
        evaluationRepository = mock(EvaluationRepository::class.java)
        repo = CoursRepository(InstantAppExecutors(), signetsApi, dao)
    }

    @Test
    fun testLoadFromNetwork() {
        val callCours: LiveData<ApiResponse<ApiSignetsModel<ApiListeDeCours>>> = ApiUtil.successCall(signetsModel)
        `when`(signetsApi.listeCours(EtudiantRequestBody(userCredentials))).thenReturn(callCours)

        val sommaireElementsEvaluation = SommaireElementsEvaluation(
                "",
                "",
                "25",
                "50",
                "50",
                "10",
                "20",
                "18,2",
                "70,6",
                "99,0",
                "96,4",
                "65,5"
        )
        val noteSur100Cours2 = ThreadLocalRandom.current().nextDouble(0.0, 100.0)
        `when`(evaluationRepository.getEvaluationsSummary(eq(userCredentials), any(), eq(true)))
                .thenAnswer(Answer<LiveData<Resource<SommaireElementsEvaluation>>> {
                    with(it.getArgument(1) as Cours) {
                        MutableLiveData<Resource<SommaireElementsEvaluation>>().apply {
                            value = Resource.success(sommaireElementsEvaluation.copy().apply {
                                this.sigleCours = this@with.sigle
                                this.session = this@with.session

                                this.noteSur100 = noteSur100Cours2.toString()
                            })
                        }
                    }
                })

        val repoLiveData: LiveData<Resource<List<Cours>>> = repo.getCours(userCredentials, true)
        val observer = mock(Observer::class.java)

        // Start observing the returned LiveData
        repoLiveData.observeForever(observer as Observer<Resource<List<Cours>>>)
        verifyNoMoreInteractions(signetsApi)

        // Verify that a loading resource has been posted by NetworkBoundResource while the cached
        // data is fetched from the DB
        verify(observer).onChanged(Resource.loading(null))

        // Prepare fake updated db data
        val updatedDbData: MutableLiveData<List<CoursEntity>> = MutableLiveData()
        /*
         After fetching from the network, the data will be stored in the DB. NetworkBoundResource
         would then fetch the new data from the DB. When requested, this fake updated db data will
         be returned.
          */
        `when`(dao.getAll()).thenReturn(updatedDbData)

        // Post the result of the first call to the DB
        dbData.postValue(ArrayList())
        // Check if a Request was sent to fetch new data
        verify(signetsApi).listeCours(EtudiantRequestBody(userCredentials))

        // After fetching from the network, the data should be inserted into the DB.
        verify(dao).insertAll(capture(coursEntitiesArgumentCaptor))
        val coursEntity1 = CoursEntity(
                apiCours1.sigle,
                apiCours1.groupe,
                apiCours1.session,
                apiCours1.programmeEtudes,
                apiCours1.cote,
                apiCours1.nbCredits,
                apiCours1.titreCours
        )
        assertEquals(coursEntity1, coursEntitiesArgumentCaptor.allValues[0][0])
        val coursEntity2 = CoursEntity(
                apiCours2.sigle,
                apiCours2.groupe,
                apiCours2.session,
                apiCours2.programmeEtudes,
                apiCours2.cote,
                apiCours2.nbCredits,
                apiCours2.titreCours
        )
        assertEquals(coursEntity2, coursEntitiesArgumentCaptor.allValues[0][1])

        /*
         By now, the updated data has been stored in the DB and NetworkBoundResource has requested
         data from the DB. It is waiting for the updated data in order to send it to the client.
         */
        with(listOf(coursEntity1, coursEntity2)) {
            updatedDbData.postValue(this)
            verify(observer).onChanged(Resource.success(this.toCours()))
        }
    }

    @Test
    fun testFailToLoadFromNetwork() {
        val errorMsg = "Test error couldn't load from network"
        `when`(signetsApi.listeCours(EtudiantRequestBody(userCredentials))).thenReturn(ApiUtil.failCall(errorMsg))

        val repoLiveData: LiveData<Resource<List<Cours>>> = repo.getCours(userCredentials, true)
        val observer = mock(Observer::class.java)

        // Start observing the returned LiveData
        repoLiveData.observeForever(observer as Observer<Resource<List<Cours>>>)
        verifyNoMoreInteractions(signetsApi)

        // Verify that a loading resource has been posted by NetworkBoundResource while the cached
        // data is fetched from the DB
        verify(observer).onChanged(Resource.loading(null))

        // Post the result of the first call to the DB
        val coursEntity = CoursEntity(
                "LOG530",
                "01",
                "H2018",
                "7365",
                "A",
                3,
                "Réingénierie du logiciel"
        )
        val currentDBdata = ArrayList<CoursEntity>().apply {
            add(coursEntity)
        }
        dbData.postValue(currentDBdata)

        // Check if a Request was sent to fetch new data
        verify(signetsApi).listeCours(EtudiantRequestBody(userCredentials))

        // No data should be inserted to the DB since there was an error
        verify(dao, never()).insert(any())
        verify(dao, never()).insertAll(any())

        // Verify that observer has been notified
        verify(observer).onChanged(Resource.error(errorMsg, currentDBdata.toCours()))
    }

    @Test
    fun testLoadFromDb() {
        val repoLiveData: LiveData<Resource<List<Cours>>> = repo.getCours(userCredentials, false)
        val observer = mock(Observer::class.java)

        // Start observing the returned LiveData
        repoLiveData.observeForever(observer as Observer<Resource<List<Cours>>>)
        verifyNoMoreInteractions(signetsApi)

        // Verify that a loading resource has been posted by NetworkBoundResource while the cached
        // data is fetched from the DB
        verify(observer).onChanged(Resource.loading(null))

        // Post the result of the first call to the DB
        val coursEntity = CoursEntity(
                "LOG530",
                "01",
                "H2018",
                "7365",
                "A",
                3,
                "Réingénierie du logiciel"
        )
        val currentDBdata = ArrayList<CoursEntity>().apply {
            add(coursEntity)
        }
        dbData.postValue(currentDBdata)

        // Make sure there wasn't any interaction with SignetsApi since we are only loading from the
        // DB
        verifyNoMoreInteractions(signetsApi)

        verify(observer).onChanged(Resource.success(currentDBdata.toCours()))
    }
}