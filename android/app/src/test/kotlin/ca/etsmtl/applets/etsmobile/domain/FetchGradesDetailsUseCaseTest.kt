package ca.etsmtl.applets.etsmobile.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.mockNetwork
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.EvaluationCours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.data.model.SommaireEtEvaluations
import ca.etsmtl.applets.repository.data.model.UniversalCode
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationCoursRepository
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.Date

/**
 * Created by Sonphil on 08-12-18.
 */
@RunWith(JUnit4::class)
class FetchGradesDetailsUseCaseTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val userCredentials = SignetsUserCredentials(UniversalCode("test"), "foo")
    private val evaluationRepository = mock(EvaluationRepository::class.java)
    private val evaluationCoursRepository = mock(EvaluationCoursRepository::class.java)
    private val app = mock(App::class.java)
    private val fetchGradesDetailsUseCase = FetchGradesDetailsUseCase(
        userCredentials,
        evaluationRepository,
        evaluationCoursRepository,
        app
    )
    private val sommaireEtEvaluationsLiveData = MutableLiveData<Resource<SommaireEtEvaluations>>()
    private val cours = Cours(
        "LOG123",
        "05",
        "H2018",
        "1234",
        "a+",
        "95",
        4,
        "Foo"
    )
    private val observer: Observer<Resource<SommaireEtEvaluations>> = mock()
    private val evaluationCoursLiveData = MutableLiveData<Resource<List<EvaluationCours>>>()
    private val noInternetErrorMsg = "Pas d'Internet"
    private val genericErrorMsg = "Error"
    private val evaluationsIncompleteErrorMsg = "Évaluations incomplètes"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.error_no_internet_connection)).thenReturn(noInternetErrorMsg)
        `when`(app.getString(R.string.error)).thenReturn(genericErrorMsg)
        `when`(app.getString(R.string.error_course_evaluations_not_completed)).thenReturn(evaluationsIncompleteErrorMsg)
        `when`(evaluationRepository.getSummaryAndEvaluations(
            eq(userCredentials),
            any(),
            eq(true)
        )).thenReturn(sommaireEtEvaluationsLiveData)
        `when`(evaluationCoursRepository.getEvaluationCours(
            eq(userCredentials),
            any(),
            eq(true)
        )).thenReturn(evaluationCoursLiveData)
    }

    @Test
    fun testLoading() {
        // given
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        val expectedRes: Resource<SommaireEtEvaluations> = Resource.loading(null)

        // when
        sommaireEtEvaluationsLiveData.postValue(expectedRes)

        // then
        verify(observer).onChanged(expectedRes)
    }

    @Test
    fun testSuccess() {
        // given
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        val sommaireElementsEvaluation = SommaireElementsEvaluation(
            "LOG530",
            "H2018",
            "95",
            "100",
            "95",
            "95",
            "95",
            "0",
            "95",
            "50",
            "95",
            "95"
        )
        val expectedRes: Resource<SommaireEtEvaluations> = Resource.success(SommaireEtEvaluations(sommaireElementsEvaluation, emptyList()))

        // when
        sommaireEtEvaluationsLiveData.postValue(expectedRes)

        // then
        verify(observer).onChanged(expectedRes)
    }

    @Test
    fun testErrorNoInternet() {
        // given
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        val resultRes: Resource<SommaireEtEvaluations> = Resource.error("Host not found", null)
        val expectedRes: Resource<SommaireEtEvaluations> = Resource.error(noInternetErrorMsg, null)

        // when
        app.mockNetwork(false)
        sommaireEtEvaluationsLiveData.postValue(resultRes)

        // then
        verify(observer).onChanged(expectedRes)
    }

    @Test
    fun testError_evaluationCoursLoading() {
        // given
        app.mockNetwork(true)
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        sommaireEtEvaluationsLiveData.postValue(Resource.error("Unknown error", null))

        // when
        evaluationCoursLiveData.postValue(Resource.loading(null))

        // then
        verify(observer).onChanged(Resource.loading(null))
    }

    @Test
    fun testError_evaluationCoursError() {
        // given
        app.mockNetwork(true)
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        sommaireEtEvaluationsLiveData.postValue(Resource.error("Error", null))

        // when
        evaluationCoursLiveData.postValue(Resource.error(genericErrorMsg, null))

        // then
        verify(observer).onChanged(Resource.error(genericErrorMsg, null))
    }

    @Test
    fun testError_evaluationCoursCompleted() {
        // given
        app.mockNetwork(true)
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        sommaireEtEvaluationsLiveData.postValue(Resource.error("Error", null))

        // when
        evaluationCoursLiveData.postValue(Resource.success(listOf(
            EvaluationCours(
                "H2018",
                Date(),
                Date(),
                "",
                true,
                "",
                cours.sigle,
                ""
            ),
            EvaluationCours(
                "H2018",
                Date(),
                Date(),
                "",
                false,
                "",
                "GPA123",
                ""
            )
        )))

        // then
        verify(observer).onChanged(Resource.error(genericErrorMsg, null))
    }

    @Test
    fun testError_evaluationCoursNotCompleted() {
        // given
        app.mockNetwork(true)
        fetchGradesDetailsUseCase(cours).observeForever(observer)
        sommaireEtEvaluationsLiveData.postValue(Resource.error("Error", null))

        // when
        evaluationCoursLiveData.postValue(Resource.success(listOf(
            EvaluationCours(
                "H2018",
                Date(),
                Date(),
                "",
                false,
                "",
                cours.sigle,
                "Cours"
            ),
            EvaluationCours(
                "H2018",
                Date(),
                Date(),
                "",
                true,
                "",
                cours.sigle,
                "Calculatrice"
            ),
            EvaluationCours(
                "H2018",
                Date(),
                Date(),
                "",
                false,
                "",
                "GPA123",
                ""
            )
        )))

        // then
        verify(observer).onChanged(Resource.error(evaluationsIncompleteErrorMsg, null))
    }
}