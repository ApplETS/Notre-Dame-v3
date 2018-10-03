package ca.etsmtl.applets.etsmobile.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.mockNetwork
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

/**
 * Created by Sonphil on 16-08-18.
 */
@RunWith(JUnit4::class)
class GradesViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coursRepository: CoursRepository = mock()
    private val app: App = mock()
    private val userCredentials = SignetsUserCredentials("test", "foo")
    private val gradesViewModel = GradesViewModel(coursRepository, userCredentials, app)
    @Captor
    private lateinit var messageEventArgumentCaptor: ArgumentCaptor<Event<String?>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.session_fall)).thenReturn("Automne")
        `when`(app.getString(R.string.session_winter)).thenReturn("Hiver")
        `when`(app.getString(R.string.session_summer)).thenReturn("Été")
        `when`(app.getString(R.string.session_without)).thenReturn("Sans trimestre")
        `when`(app.getString(R.string.error_no_internet_connection)).thenReturn("Absence de connexion internet")
        `when`(app.getString(R.string.error)).thenReturn("Une erreur est survenue")
    }
    @Test
    fun testGetCours() {
        val liveData = MutableLiveData<Resource<List<Cours>>>()
        liveData.value = Resource.loading(null)
        `when`(coursRepository.getCours(userCredentials, true)).thenReturn(liveData)

        gradesViewModel.refresh()

        val observer: Observer<Map<String, List<Cours>>> = mock()
        gradesViewModel.getCours().observeForever(observer)
        verify(observer).onChanged(null)

        liveData.postValue(Resource.error("test error", null))
        verify(observer, times(2)).onChanged(null)

        val cours1 = Cours(
                "MAT123",
                "01",
                "s.o",
                "7365",
                "K",
                "91",
                3,
                "Math"
        )
        val cours2 = Cours(
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
        verify(observer).onChanged(mutableMapOf<String, List<Cours>>().apply {
            put("Hiver 2018", listOf(cours5, cours4))
            put("Été 2016", listOf(cours3))
            put("Sans trimestre", listOf(cours2, cours1))
        })
    }

    @Test
    fun testLoading() {
        val liveData = MutableLiveData<Resource<List<Cours>>>()
        liveData.value = Resource.loading(null)
        `when`(coursRepository.getCours(userCredentials, true)).thenReturn(liveData)

        gradesViewModel.refresh()

        val observer: Observer<Boolean> = mock()
        gradesViewModel.getLoading().observeForever(observer)
        verify(observer).onChanged(true)

        liveData.postValue(Resource.error("test error", null))
        verify(observer).onChanged(false)
    }

    @Test
    fun testMessage() {
        val liveData = MutableLiveData<Resource<List<Cours>>>()
        `when`(coursRepository.getCours(userCredentials, true)).thenReturn(liveData)

        gradesViewModel.refresh()

        val observer: Observer<Event<String?>> = mock()
        gradesViewModel.errorMessage.observeForever(observer)

        app.mockNetwork()

        liveData.postValue(Resource.error("test error", null))
        verify(observer).onChanged(capture(messageEventArgumentCaptor))
        assertEquals("Une erreur est survenue", messageEventArgumentCaptor.value.getContentIfNotHandled())

        app.mockNetwork(false)

        liveData.postValue(Resource.error("test error", null))
        verify(observer, times(2)).onChanged(capture(messageEventArgumentCaptor))
        assertEquals("Absence de connexion internet", messageEventArgumentCaptor.value.getContentIfNotHandled())
    }

    @Test
    fun testShowEmptyView() {
        val liveData = MutableLiveData<Resource<List<Cours>>>()
        `when`(coursRepository.getCours(userCredentials, true)).thenReturn(liveData)

        gradesViewModel.refresh()

        val observer: Observer<Boolean> = mock()
        gradesViewModel.getShowEmptyView().observeForever(observer)

        liveData.value = Resource.success(emptyList())
        verify(observer).onChanged(true)

        liveData.value = Resource.loading(null)
        verify(observer).onChanged(false)

        liveData.value = Resource.success(listOf(Cours(
                "MAT123",
                "01",
                "s.o",
                "7365",
                "K",
                "91",
                3,
                "Math"
        )))
        verify(observer, times(2)).onChanged(false)
    }
}