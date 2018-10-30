package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchCurrentSessionSeancesUseCase
import ca.etsmtl.applets.etsmobile.presentation.schedule.ScheduleViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
Created by mykaelll87 on 2018-10-29
 */
@RunWith(JUnit4::class)
class ScheduleViewModelTest {
    @Rule
    @JvmField
    val insantExecutorRule = InstantTaskExecutorRule()

    private val app: App = mock()
    private val credentials: SignetsUserCredentials = mock()
    private val fetchCurrentSessionSeancesUseCase: FetchCurrentSessionSeancesUseCase = mock()
    private val scheduleViewModel = ScheduleViewModel(credentials,fetchCurrentSessionSeancesUseCase, app)
    @Captor
    private lateinit var messageEventArgumentCaptor: ArgumentCaptor<Event<String?>>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        `when`(app.getString(R.string.error_no_internet_connection)).thenReturn("Absence de connexion internet")
        `when`(app.getString(R.string.error)).thenReturn("Une erreur est survenue")
    }
}