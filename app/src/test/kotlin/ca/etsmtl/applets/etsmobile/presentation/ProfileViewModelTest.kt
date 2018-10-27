package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchEtudiantUseCase
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileHeaderItem
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileItem
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileViewModel
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.xwray.groupie.Section
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
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
    private val profileViewModel = ProfileViewModel(fetchEtudiantUseCase, app)
    @Captor
    private lateinit var sectionsArgumentCaptor: ArgumentCaptor<List<Section>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(app.getString(R.string.title_student_status_profile)).thenReturn("fooItem1")
        `when`(app.getString(R.string.label_balance_profile)).thenReturn("fooItem2")
        `when`(app.getString(R.string.title_personal_information_profile)).thenReturn("fooItem3")
        `when`(app.getString(R.string.label_first_name_profile)).thenReturn("fooItem4")
        `when`(app.getString(R.string.label_last_name_profile)).thenReturn("fooItem5")
        `when`(app.getString(R.string.label_permanent_code_profile)).thenReturn("fooItem6")
    }

    @Test
    fun testCallUseCase() {
        val foo = MutableLiveData<Resource<Etudiant>>()
        `when`(fetchEtudiantUseCase(any())).thenReturn(foo)

        profileViewModel.refresh()

        verify(fetchEtudiantUseCase).invoke(any())
    }

    @Test
    fun testSendResultToUI() {
        val foo = MutableLiveData<Resource<Etudiant>>()
        `when`(fetchEtudiantUseCase(any())).thenReturn(foo)

        val etudiantObserver = mock<Observer<List<Section>>>()
        verify(etudiantObserver, Mockito.never()).onChanged(ArgumentMatchers.any())
        val loadingObserver = mock<Observer<Boolean>>()
        verify(loadingObserver, Mockito.never()).onChanged(ArgumentMatchers.any())
        val errorMsgObserver = mock<Observer<String>>()
        verify(errorMsgObserver, Mockito.never()).onChanged(ArgumentMatchers.any())

        profileViewModel.profile.observeForever(etudiantObserver)
        profileViewModel.loading.observeForever(loadingObserver)
        profileViewModel.errorMessage.observeForever(errorMsgObserver)

        profileViewModel.refresh()

        var fooRes: Resource<Etudiant> = Resource.loading(null)
        foo.value = fooRes
        verifyZeroInteractions(etudiantObserver)
        verify(loadingObserver).onChanged(true)

        reset(etudiantObserver)
        reset(loadingObserver)

        val fooEtudiant = Etudiant("testFoo", "foo", "foo", "foo", "123,00", true)
        fooRes = Resource.success(fooEtudiant)
        foo.value = fooRes
        val expectedSections = mutableListOf<Section>()

        val expectedSection0Header = ProfileHeaderItem("fooItem1")
        val expectedSection0Item0 = ProfileItem("fooItem2", fooEtudiant.soldeTotal)
        expectedSections.add(
                Section().apply {
                    setHeader(expectedSection0Header)
                    add(expectedSection0Item0)
                }
        )

        val expectedSection1Header = ProfileHeaderItem("fooItem3")
        val expectedSection1Item0 = ProfileItem("fooItem4", fooEtudiant.prenom)
        val expectedSection1Item1 = ProfileItem("fooItem5", fooEtudiant.nom)
        val expectedSection1Item2 = ProfileItem("fooItem6", fooEtudiant.codePerm)
        expectedSections.add(
                Section().apply {
                    setHeader(expectedSection1Header)
                    add(expectedSection1Item0)
                    add(expectedSection1Item1)
                    add(expectedSection1Item2)
                }
        )

        verify(etudiantObserver).onChanged(capture(sectionsArgumentCaptor))
        assertEquals(expectedSections.size, sectionsArgumentCaptor.value.size)
        sectionsArgumentCaptor.value.forEachIndexed { index, section ->
            if (index == 0) {
                assertEquals(expectedSection0Header, section.getItem(0))
                assertEquals(expectedSection0Item0, section.getItem(1))
            } else {
                assertEquals(expectedSection1Header, section.getItem(0))
                assertEquals(expectedSection1Item0, section.getItem(1))
                assertEquals(expectedSection1Item1, section.getItem(2))
                assertEquals(expectedSection1Item2, section.getItem(3))
            }
        }
        verify(loadingObserver).onChanged(false)
        verify(errorMsgObserver).onChanged(null)

        reset(etudiantObserver)
        reset(loadingObserver)
        reset(errorMsgObserver)

        val errorMsg = "Test error"
        fooRes = Resource.error(errorMsg, null)
        foo.value = fooRes
        verify(etudiantObserver).onChanged(emptyList())
        verify(loadingObserver).onChanged(false)
        verify(errorMsgObserver).onChanged(errorMsg)
    }
}