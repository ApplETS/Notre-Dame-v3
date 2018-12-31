package ca.etsmtl.applets.etsmobile.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.data.model.UniversalCode
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

/**
 * Created by Sonphil on 11-10-18.
 */
@RunWith(JUnit4::class)
class FetchGradesCoursesUseCaseTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val userCredentials = SignetsUserCredentials(UniversalCode("test"), "foo")
    private val coursRepository: CoursRepository = mock(CoursRepository::class.java)
    private val evaluationRepository: EvaluationRepository = mock(EvaluationRepository::class.java)
    private val app: App = mock(App::class.java)
    private val fetchGradesCoursesUseCase = FetchGradesCoursesUseCase(
            userCredentials,
            coursRepository,
            evaluationRepository,
            app
    )
    @Captor
    private lateinit var resArgumentCaptor: ArgumentCaptor<Resource<Map<String, List<Cours>>>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.session_fall)).thenReturn("Automne")
        `when`(app.getString(R.string.session_winter)).thenReturn("Hiver")
        `when`(app.getString(R.string.session_summer)).thenReturn("Été")
        `when`(app.getString(R.string.session_without)).thenReturn("Sans trimestre")
    }

    @Test
    fun testGetCours() {
        val liveData = MutableLiveData<Resource<List<Cours>>>()
        liveData.postValue(Resource.loading(null))
        `when`(coursRepository.getCours(userCredentials)).thenReturn(liveData)

        val observer: Observer<Resource<Map<String, List<Cours>>>> = mock()
        fetchGradesCoursesUseCase().observeForever(observer)
        verify(coursRepository).getCours(userCredentials)
        verify(observer).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)

        val errorMsg = "test error"
        liveData.postValue(Resource.error(errorMsg, null))
        verify(observer, times(2)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.ERROR, resArgumentCaptor.value.status)
        assertEquals(errorMsg, resArgumentCaptor.value.message)

        var cours1 = Cours(
                "MAT123",
                "01",
                "s.o",
                "7365",
                "K",
                "91",
                3,
                "Math"
        )
        var cours2 = Cours(
                "CHI123",
                "01",
                "s.o",
                "7365",
                "K",
                "91",
                3,
                "Chimie"
        )
        val cours3 = Cours(
                "LOG123",
                "01",
                "É2016",
                "7365",
                "A",
                "91àà",
                3,
                "Foo"
        )
        val cours4 = Cours(
                "LOG530",
                "01",
                "H2018",
                "7365",
                "A",
                "91",
                3,
                "Réingénierie du logiciel"
        )
        val cours5 = Cours(
                "LOG432",
                "01",
                "H2018",
                "7365",
                "A",
                "91",
                3,
                "Oof"
        )
        liveData.postValue(Resource.success(mutableListOf<Cours>().apply {
            add(cours1)
            add(cours2)
            add(cours3)
            add(cours4)
            add(cours5)
        }))
        verify(observer, times(3)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.SUCCESS, resArgumentCaptor.value.status)
        assertEquals(mutableMapOf<String, List<Cours>>().apply {
            put("Hiver 2018", listOf(cours5, cours4))
            put("Été 2016", listOf(cours3))
            put("Sans trimestre", listOf(cours2, cours1))
        }, resArgumentCaptor.value.data)
    }

    @Test
    fun testGetCoursWithMissingGrades() {
        var cours1 = Cours(
                "MAT123",
                "01",
                "s.o",
                "7365",
                "K",
                "91",
                3,
                "Math"
        )
        var cours2 = Cours(
                "CHI123",
                "01",
                "s.o",
                "7365",
                "K",
                "91",
                3,
                "Chimie"
        )
        val cours3 = Cours(
                "LOG123",
                "01",
                "É2016",
                "7365",
                "A",
                "91",
                3,
                "Foo"
        )
        val cours4 = Cours(
                "LOG530",
                "01",
                "H2018",
                "7365",
                "",
                "91",
                3,
                "Réingénierie du logiciel"
        )
        val coursWithMissingRatingAndGrade = Cours(
                "LOG432",
                "01",
                "H2018",
                "7365",
                "",
                "",
                3,
                "Oof"
        )

        val coursesLiveData = MutableLiveData<Resource<List<Cours>>>()
        `when`(coursRepository.getCours(userCredentials)).thenReturn(coursesLiveData)
        val summaryLiveData = MutableLiveData<Resource<SommaireElementsEvaluation>>()
        `when`(evaluationRepository.getEvaluationsSummary(userCredentials, coursWithMissingRatingAndGrade)).thenReturn(summaryLiveData)

        val observer: Observer<Resource<Map<String, List<Cours>>>> = mock()
        fetchGradesCoursesUseCase().observeForever(observer)

        coursesLiveData.postValue(Resource.success(mutableListOf<Cours>().apply {
            add(cours1)
            add(cours2)
            add(cours3)
            add(cours4)
            add(coursWithMissingRatingAndGrade)
        }))
        verify(evaluationRepository).getEvaluationsSummary(userCredentials, coursWithMissingRatingAndGrade)

        verify(observer, times(1)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)

        summaryLiveData.postValue(Resource.error("Test", null))

        verify(observer, times(2)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.SUCCESS, resArgumentCaptor.value.status)
    }
}