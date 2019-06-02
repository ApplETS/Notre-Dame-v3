package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchEtudiantUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchProgrammesUseCase
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileAdapter
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileHeaderItem
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileItem
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileValueItem
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.extension.mockNetwork
import model.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.mock
import model.Etudiant
import model.Programme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

/**
 * Created by Sonphil on 28-04-18.
 */

class ProfileViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val app: App = mock()
    private val fetchEtudiantUseCase: FetchEtudiantUseCase = mock(FetchEtudiantUseCase::class.java)
    private val fetchProgrammesUseCase: FetchProgrammesUseCase = mock(FetchProgrammesUseCase::class.java)
    private val profileViewModel = ProfileViewModel(fetchEtudiantUseCase, fetchProgrammesUseCase, app)
    private val etudiantLiveData = MutableLiveData<Resource<Etudiant>>()
    private val programmesLiveData = MutableLiveData<Resource<List<Programme>>>()
    private val profileObserver = mock<Observer<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>>>()
    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorMsgObserver = mock<EventObserver<String?>>()
    @Captor
    private lateinit var sectionsArgumentCaptor: ArgumentCaptor<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>>
    @Captor
    private lateinit var stringEventArgumentCaptor: ArgumentCaptor<Event<String?>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.title_student_status_profile)).thenReturn("fooItem1")
        `when`(app.getString(R.string.label_balance_profile)).thenReturn("fooItem2")
        `when`(app.getString(R.string.title_personal_information_profile)).thenReturn("fooItem3")
        `when`(app.getString(R.string.label_first_name_profile)).thenReturn("fooItem4")
        `when`(app.getString(R.string.label_last_name_profile)).thenReturn("fooItem5")
        `when`(app.getString(R.string.label_permanent_code_profile)).thenReturn("fooItem6")

        `when`(fetchEtudiantUseCase(any())).thenReturn(etudiantLiveData)
        `when`(fetchProgrammesUseCase()).thenReturn(programmesLiveData)

        profileViewModel.profile.observeForever(profileObserver)
        profileViewModel.loading.observeForever(loadingObserver)
        profileViewModel.errorMessage.observeForever(errorMsgObserver)
    }

    @Test
    fun testCallUseCases() {
        profileViewModel.refresh()

        verify(fetchEtudiantUseCase).invoke(any())
        verify(fetchProgrammesUseCase).invoke()
    }

    @Test
    fun testSendResultToUI() {
        profileViewModel.refresh()

        verify(loadingObserver).onChanged(true)

        var fooRes: Resource<Etudiant> = Resource.loading(null)
        etudiantLiveData.value = fooRes
        verify(profileObserver, times(2)).onChanged(emptyList())
        verify(loadingObserver, times(2)).onChanged(true)

        reset(profileObserver)
        reset(loadingObserver)

        val fooEtudiant = Etudiant("testFoo", "foo", "foo", "foo", "123,00", true)
        fooRes = Resource.success(fooEtudiant)
        etudiantLiveData.value = fooRes
        verify(profileObserver).onChanged(capture(sectionsArgumentCaptor))
        assertEquals(0, sectionsArgumentCaptor.value.size)

        val fooProgrammes = emptyList<Programme>()
        programmesLiveData.value = Resource.success(fooProgrammes)
        val expectedSections = mutableListOf<ProfileItem<out ProfileAdapter.ProfileViewHolder>>()

        val expectedSection0Header = ProfileHeaderItem("fooItem1")
        val expectedSection0Item0 = ProfileValueItem("fooItem2", fooEtudiant.soldeTotal)
        expectedSections.add(expectedSection0Header)
        expectedSections.add(expectedSection0Item0)

        val expectedSection1Header = ProfileHeaderItem("fooItem3")
        val expectedSection1Item0 = ProfileValueItem("fooItem4", fooEtudiant.prenom)
        val expectedSection1Item1 = ProfileValueItem("fooItem5", fooEtudiant.nom)
        val expectedSection1Item2 = ProfileValueItem("fooItem6", fooEtudiant.codePerm)
        expectedSections.add(expectedSection1Header)
        expectedSections.add(expectedSection1Item0)
        expectedSections.add(expectedSection1Item1)
        expectedSections.add(expectedSection1Item2)

        verify(profileObserver, times(2)).onChanged(capture(sectionsArgumentCaptor))
        assertEquals(expectedSections.size, sectionsArgumentCaptor.value.size)
        assertEquals(expectedSection0Header, sectionsArgumentCaptor.value[0])
        assertEquals(expectedSection0Item0, sectionsArgumentCaptor.value[1])
        assertEquals(expectedSection1Header, sectionsArgumentCaptor.value[2])
        assertEquals(expectedSection1Item0, sectionsArgumentCaptor.value[3])
        assertEquals(expectedSection1Item1, sectionsArgumentCaptor.value[4])
        assertEquals(expectedSection1Item2, sectionsArgumentCaptor.value[5])
        verify(loadingObserver).onChanged(false)
        verify(errorMsgObserver, times(4)).onChanged(capture(stringEventArgumentCaptor))
        assertEquals(null, stringEventArgumentCaptor.value.peekContent())
    }

    @Test
    fun testSendErrorToUI() {
        app.mockNetwork(true)

        profileViewModel.refresh()

        val expectedError = "Test error"
        `when`(app.getString(R.string.error)).thenReturn(expectedError)
        val errorMsg = "Test error"
        etudiantLiveData.value = Resource.error(errorMsg, null)
        verify(errorMsgObserver, times(2)).onChanged(capture(stringEventArgumentCaptor))
        assertEquals(null, stringEventArgumentCaptor.value.peekContent())

        programmesLiveData.value = Resource.error(errorMsg, null)
        verify(errorMsgObserver, times(3)).onChanged(capture(stringEventArgumentCaptor))
        assertEquals(errorMsg, stringEventArgumentCaptor.value.peekContent())
    }
}