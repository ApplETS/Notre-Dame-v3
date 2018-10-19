package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchGradesCoursesUseCase
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.mockNetwork
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
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

    private val app: App = mock()
    private val fetchGradesCoursesUseCase: FetchGradesCoursesUseCase = mock()
    private val gradesViewModel = GradesViewModel(fetchGradesCoursesUseCase, app)
    @Captor
    private lateinit var messageEventArgumentCaptor: ArgumentCaptor<Event<String?>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.error_no_internet_connection)).thenReturn("Absence de connexion internet")
        `when`(app.getString(R.string.error)).thenReturn("Une erreur est survenue")
    }

    @Test
    fun testLoading() {
        val liveData = MutableLiveData<Resource<Map<String, List<Cours>>>>()
        liveData.value = Resource.loading(null)
        `when`(fetchGradesCoursesUseCase()).thenReturn(liveData)

        gradesViewModel.refresh()

        val observer: Observer<Boolean> = mock()
        gradesViewModel.getLoading().observeForever(observer)
        verify(observer).onChanged(true)

        liveData.postValue(Resource.error("test error", null))
        verify(observer).onChanged(false)
    }

    @Test
    fun testMessage() {
        val liveData = MutableLiveData<Resource<Map<String, List<Cours>>>>()
        `when`(fetchGradesCoursesUseCase()).thenReturn(liveData)

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
        val liveData = MutableLiveData<Resource<Map<String, List<Cours>>>>()
        `when`(fetchGradesCoursesUseCase()).thenReturn(liveData)

        gradesViewModel.refresh()

        val observer: Observer<Boolean> = mock()
        gradesViewModel.getShowEmptyView().observeForever(observer)

        liveData.value = Resource.success(emptyMap())
        verify(observer).onChanged(true)

        liveData.value = Resource.loading(null)
        verify(observer).onChanged(false)

        liveData.value = Resource.success(mapOf(
                "Test" to listOf(Cours(
                        "MAT123",
                        "01",
                        "s.o",
                        "7365",
                        "K",
                        "91",
                        3,
                        "Math"
                ))
        ))
        verify(observer, times(2)).onChanged(false)
    }
}