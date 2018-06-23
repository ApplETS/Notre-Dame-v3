package ca.etsmtl.repository.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.repository.util.InstantAppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.repository.data.db.dao.signets.SommaireElementsEvaluationDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Cours
import ca.etsmtl.repository.data.model.signets.Evaluation
import ca.etsmtl.repository.data.model.signets.ListeDesElementsEvaluation
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repository.data.model.signets.SommaireElementsEvaluation
import ca.etsmtl.repository.data.repository.signets.EvaluationRepository
import ca.etsmtl.repository.util.ApiUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class EvaluationRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: EvaluationRepository
    private lateinit var evaluationDao: EvaluationDao
    private lateinit var sommaireEvaluationDao: SommaireElementsEvaluationDao
    private lateinit var evaluation: Evaluation
    private lateinit var sommaireEvaluation: SommaireElementsEvaluation
    private lateinit var evaluationList: ArrayList<Evaluation>
    private lateinit var sommaireEvaluationList: ArrayList<SommaireElementsEvaluation>
    private val evaluationDbData: MutableLiveData<List<Evaluation>> = MutableLiveData()
    private val sommaireEvaluationDbData: MutableLiveData<List<SommaireElementsEvaluation>> = MutableLiveData()
    private val codeUniversel = "test"
    private val motPasse = "foo"
    private val signetsUserCredentials = SignetsUserCredentials(codeUniversel, motPasse)
    private val sigle = "LOG123"
    private val groupe = "01"
    private val session = "É2018"
    private val cours = Cours(sigle, groupe, session, "LOG", "A", 4, "Foo")
    private val signetsModel = SignetsModel<ListeDesElementsEvaluation>()
    private val listeDesElementsEvaluation = ListeDesElementsEvaluation(
            "foo",
            "0",
            "0",
            "0",
            "0",
            "0",
            "50",
            "0",
            "0",
            listOf(),
            ""
    )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsModel.data = listeDesElementsEvaluation
        signetsApi = Mockito.mock(SignetsApi::class.java)
        evaluationDao = Mockito.mock(EvaluationDao::class.java)
        sommaireEvaluationDao = Mockito.mock(SommaireElementsEvaluationDao::class.java)
        Mockito.`when`(evaluationDao.getAll()).thenReturn(evaluationDbData)
        Mockito.`when`(sommaireEvaluationDao.getAll()).thenReturn(sommaireEvaluationDbData)
        repo = EvaluationRepository(InstantAppExecutors(), signetsApi, evaluationDao, sommaireEvaluationDao)
        evaluationList = ArrayList()
        evaluation = Evaluation(
                "foo",
                "foo",
                "foo",
                "foo",
                "foo",
                "foo",
                "foo",
                "foo",
                "foo",
                "foo",
                "99",
                "oui",
                "",
                "non"
        )
        evaluationList.add(evaluation)
        sommaireEvaluationList = ArrayList()
        sommaireEvaluation = SommaireElementsEvaluation(
                sigle,
                session,
                listeDesElementsEvaluation.noteACeJour,
                listeDesElementsEvaluation.scoreFinalSur100,
                listeDesElementsEvaluation.moyenneClasse,
                listeDesElementsEvaluation.ecartTypeClasse,
                listeDesElementsEvaluation.medianeClasse,
                listeDesElementsEvaluation.rangCentileClasse,
                listeDesElementsEvaluation.noteACeJourElementsIndividuels,
                listeDesElementsEvaluation.noteSur100PourElementsIndividuels
        )
        sommaireEvaluationList = ArrayList()
        sommaireEvaluationList.add(sommaireEvaluation)
    }

    @Test
    fun testLoadEvaluationsFromNetwork() {
        val call: LiveData<ApiResponse<SignetsModel<ListeDesElementsEvaluation>>> = ApiUtil.successCall(signetsModel)
        Mockito.`when`(signetsApi.listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)).thenReturn(call)

        val data: LiveData<Resource<List<Evaluation>>> = repo.getEvaluations(signetsUserCredentials, cours, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned
        data.observeForever(observer as Observer<Resource<List<Evaluation>>>)
        Mockito.verifyZeroInteractions(signetsApi)
        /*
        NeworkBoundResource should have posted an loading resource while the cached data is being
        fetched from the DB
        */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        // Prepare fake updated db data.
        val updatedDbData: MutableLiveData<List<Evaluation>> = MutableLiveData()
        // Return the fake updated db data when requested
        // After fetching from the network, the data will stored in the db and the db's data will be
        // returned.
        Mockito.`when`(evaluationDao.getAll()).thenReturn(updatedDbData)

        // Post null so that the data must be fetched from the network
        evaluationDbData.postValue(null)
        // Check if a request was sent to fetch new data
        Mockito.verify(signetsApi).listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)
        // After fetching from the network, the data should be inserted into the db.
        Mockito.verify(evaluationDao).insertAll(*listeDesElementsEvaluation.liste.toTypedArray())

        /*
         By now, the updated data has been stored in the db and NetworkBoundResource will get the
         updated data
         */
        updatedDbData.postValue(listeDesElementsEvaluation.liste)
        Mockito.verify(observer).onChanged(Resource.success(listeDesElementsEvaluation.liste))
    }

    @Test
    fun testLoadSummaryFromNetwork() {
        val call: LiveData<ApiResponse<SignetsModel<ListeDesElementsEvaluation>>> = ApiUtil.successCall(signetsModel)
        Mockito.`when`(signetsApi.listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)).thenReturn(call)

        val data: LiveData<Resource<SommaireElementsEvaluation>> = repo.getEvaluationsSummary(signetsUserCredentials, cours, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned
        data.observeForever(observer as Observer<Resource<SommaireElementsEvaluation>>)
        Mockito.verifyZeroInteractions(signetsApi)
        /*
        NeworkBoundResource should have posted an loading resource while the cached data is being
        fetched from the DB
        */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        // Prepare fake updated db data.
        val updatedDbData: MutableLiveData<List<SommaireElementsEvaluation>> = MutableLiveData()
        // Return the fake updated db data when requested
        // After fetching from the network, the data will stored in the db and the db's data will be
        // returned.
        Mockito.`when`(sommaireEvaluationDao.getAll()).thenReturn(updatedDbData)

        // Post null so that the data must be fetched from the network
        sommaireEvaluationDbData.postValue(null)
        // Check if a request was sent to fetch new data
        Mockito.verify(signetsApi).listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)
        // After fetching from the network, the data should be inserted into the db.
        Mockito.verify(sommaireEvaluationDao).insert(sommaireEvaluation)

        /*
         By now, the updated data has been stored in the db and NetworkBoundResource will get the
         updated data
         */
        val summaryList = ArrayList<SommaireElementsEvaluation>()
        summaryList.add(sommaireEvaluation)
        updatedDbData.postValue(summaryList)
        Mockito.verify(observer).onChanged(Resource.success(sommaireEvaluation))
    }

    @Test
    fun testFailToLoadEvaluationsFromNetwork() {
        val errorMsg = "Test error"
        val call: LiveData<ApiResponse<SignetsModel<ListeDesElementsEvaluation>>> = ApiUtil.failCall(errorMsg)
        Mockito.`when`(signetsApi.listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)).thenReturn(call)

        val data: LiveData<Resource<List<Evaluation>>> = repo.getEvaluations(signetsUserCredentials, cours, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned by SignetsApi
        data.observeForever(observer as Observer<Resource<List<Evaluation>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        evaluationDbData.postValue(ArrayList())
        // Check that a request has been sent to fetch new data
        Mockito.verify(signetsApi).listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)
        // The data should not be inserted to the DB since there was an error
        Mockito.verify(evaluationDao, Mockito.never()).insertAll()
        // Verify that observer has been notified
        Mockito.verify(observer).onChanged(Resource.error(errorMsg, listeDesElementsEvaluation.liste))
    }

    @Test
    fun testFailToLoadSummaryFromNetwork() {
        val errorMsg = "Test error"
        val call: LiveData<ApiResponse<SignetsModel<ListeDesElementsEvaluation>>> = ApiUtil.failCall(errorMsg)
        Mockito.`when`(signetsApi.listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)).thenReturn(call)

        val data: LiveData<Resource<SommaireElementsEvaluation>> = repo.getEvaluationsSummary(signetsUserCredentials, cours, true)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData returned by SignetsApi
        data.observeForever(observer as Observer<Resource<SommaireElementsEvaluation>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        sommaireEvaluationDbData.postValue(ArrayList())
        // Check that a request has been sent to fetch new data
        Mockito.verify(signetsApi).listeDesElementsEvaluation(codeUniversel, motPasse, sigle, groupe, session)
        // The data should not be inserted to the DB since there was an error
        Mockito.verify(evaluationDao, Mockito.never()).insertAll()
        // Verify that observer has been notified
        Mockito.verify(observer).onChanged(Resource.error(errorMsg, null))
    }

    @Test
    fun testLoadEvaluationsFromDb() {
        val data: LiveData<Resource<List<Evaluation>>> = repo.getEvaluations(signetsUserCredentials, cours, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<List<Evaluation>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val fooListEvaluation = ArrayList<Evaluation>()
        evaluationDbData.postValue(fooListEvaluation )

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.success(fooListEvaluation))
    }

    @Test
    fun testFailToLoadEvaluationsFromDb() {
        val data: LiveData<Resource<List<Evaluation>>> = repo.getEvaluations(signetsUserCredentials, cours, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<List<Evaluation>>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        evaluationDbData.postValue(null)

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.error(NetworkBoundResource.MSG_NO_DATA_DB, null))
    }

    @Test
    fun testLoadSummaryFromDb() {
        val data: LiveData<Resource<SommaireElementsEvaluation>> = repo.getEvaluationsSummary(signetsUserCredentials, cours, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<SommaireElementsEvaluation>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val fooListSummary = ArrayList<SommaireElementsEvaluation>()
        fooListSummary.add(sommaireEvaluation)
        sommaireEvaluationDbData.postValue(fooListSummary)

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.success(sommaireEvaluation))
    }

    @Test
    fun testFailToLoadSummaryFromDb() {
        val data: LiveData<Resource<SommaireElementsEvaluation>> = repo.getEvaluationsSummary(signetsUserCredentials, cours, false)
        val observer = Mockito.mock(Observer::class.java)
        // Start observing the LiveData
        data.observeForever(observer as Observer<Resource<SommaireElementsEvaluation>>)
        Mockito.verifyNoMoreInteractions(signetsApi)
        /*
         NeworkBoundResource should have posted an loading resource while the cached data is being
         fetched from the DB
          */
        Mockito.verify(observer).onChanged(Resource.loading(null))

        sommaireEvaluationDbData.postValue(null)

        // Make sure there wasn't any interaction with SignetsApi
        Mockito.verifyNoMoreInteractions(signetsApi)

        Mockito.verify(observer).onChanged(Resource.error(NetworkBoundResource.MSG_NO_DATA_DB, null))
    }
}