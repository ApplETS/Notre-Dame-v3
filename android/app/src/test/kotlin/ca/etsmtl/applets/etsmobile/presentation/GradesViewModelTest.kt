package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchGradesCoursesGroupedBySessionUseCase
import ca.etsmtl.applets.etsmobile.extension.mockNetwork
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import model.Resource
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import model.Cours
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
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
    private val fetchGradesCoursesGroupedBySessionUseCase: FetchGradesCoursesGroupedBySessionUseCase = mock()
    private val gradesViewModel = GradesViewModel(fetchGradesCoursesGroupedBySessionUseCase, app)
    @Captor
    private lateinit var messageEventArgumentCaptor: ArgumentCaptor<Event<String?>>
    private val coursLiveData = MutableLiveData<Resource<Map<String, List<Cours>>>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.error_no_internet_connection)).thenReturn("Absence de connexion internet")
        `when`(app.getString(R.string.error)).thenReturn("Une erreur est survenue")

        `when`(fetchGradesCoursesGroupedBySessionUseCase()).thenReturn(coursLiveData)
    }

    @Test
    fun loading_Loading_EmitsTrue() {
        // given
        val observer: Observer<Boolean> = mock()
        gradesViewModel.loading.observeForever(observer)

        // when
        gradesViewModel.refresh()
        coursLiveData.value = Resource.loading(null)

        // then
        verify(observer).onChanged(true)
    }

    @Test
    fun message_NonNetworkError_IsGenericErrorMessage() {
        // given
        val observer: Observer<Event<String?>> = mock()
        gradesViewModel.errorMessage.observeForever(observer)
        app.mockNetwork()

        // when
        gradesViewModel.refresh()
        coursLiveData.postValue(Resource.error("test error", null))

        // then
        verify(observer).onChanged(capture(messageEventArgumentCaptor))
        assertEquals("Une erreur est survenue", messageEventArgumentCaptor.value.getContentIfNotHandled())
    }

    @Test
    fun message_NetworkError_IsNetworkErrorMessage() {
        // given
        val observer: Observer<Event<String?>> = mock()
        gradesViewModel.errorMessage.observeForever(observer)
        app.mockNetwork(false)

        // when
        gradesViewModel.refresh()
        coursLiveData.postValue(Resource.error("test error", null))

        // then
        verify(observer).onChanged(capture(messageEventArgumentCaptor))
        assertEquals("Absence de connexion internet", messageEventArgumentCaptor.value.getContentIfNotHandled())
    }

    @Test
    fun emptyView_LoadedEmptyResult_BecomesVisible() {
        // given
        val observer: Observer<Boolean> = mock()
        gradesViewModel.showEmptyView.observeForever(observer)

        // when
        gradesViewModel.refresh()
        coursLiveData.value = Resource.success(emptyMap())

        // then
        verify(observer).onChanged(true)
    }

    @Test
    fun emptyView_Loading_BecomesHidden() {
        // given
        val observer: Observer<Boolean> = mock()
        gradesViewModel.showEmptyView.observeForever(observer)

        // when
        gradesViewModel.refresh()
        coursLiveData.value = Resource.loading(null)

        // then
        verify(observer).onChanged(false)
    }

    @Test
    fun emptyView_FinishedLoading_BecomesHidden() {
        // given
        val observer: Observer<Boolean> = mock()
        gradesViewModel.showEmptyView.observeForever(observer)

        // when
        gradesViewModel.refresh()
        coursLiveData.value = Resource.success(mapOf(
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

        // then
        verify(observer).onChanged(false)
    }
}