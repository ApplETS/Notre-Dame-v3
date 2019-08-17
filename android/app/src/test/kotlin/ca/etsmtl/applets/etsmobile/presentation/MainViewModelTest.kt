package ca.etsmtl.applets.etsmobile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ca.etsmtl.applets.etsmobile.presentation.main.Destination
import ca.etsmtl.applets.etsmobile.presentation.main.MainViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 11-05-19.
 */

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mainViewModel = MainViewModel()
    private val closeAppObserver: Observer<Event<Unit>> = mock()
    private val navigateToDestinationObserver: Observer<Event<Destination>> = mock()
    private val navigateBackObserver: Observer<Event<Unit>> = mock()
    @Captor
    private lateinit var destinationEventArgumentCaptor: ArgumentCaptor<Event<Destination>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        mainViewModel.closeApp.observeForever(closeAppObserver)
        mainViewModel.navigateToDestination.observeForever(navigateToDestinationObserver)
        mainViewModel.navigateBack.observeForever(navigateBackObserver)
    }

    @Test
    fun topLevelDestination() {
        val topLevelDestinations = mainViewModel.topLevelDestinations

        assertTrue(topLevelDestinations.contains(Destination.DASHBOARD))
        assertTrue(topLevelDestinations.contains(Destination.SCHEDULE))
        assertTrue(topLevelDestinations.contains(Destination.STUDENT))
        assertTrue(topLevelDestinations.contains(Destination.ETS))
        assertTrue(topLevelDestinations.contains(Destination.MORE))
        assertFalse(topLevelDestinations.contains(Destination.SPLASH))
        assertFalse(topLevelDestinations.contains(Destination.LOGIN))
        assertFalse(topLevelDestinations.contains(Destination.OTHER))
    }

    @Test
    fun bottomNavigation_OnSplashScreen_ReturnsFalse() {
        // given
        mainViewModel.onNavigationDestinationChanged(Destination.SPLASH)

        // when
        val shouldPerform = mainViewModel.shouldPerformBottomNavigationViewAction()

        // then
        assertFalse(shouldPerform)
    }

    @Test
    fun bottomNavigation_OnLoginScreen_ReturnsFalse() {
        // given
        mainViewModel.onNavigationDestinationChanged(Destination.LOGIN)

        // when
        val shouldPerform = mainViewModel.shouldPerformBottomNavigationViewAction()

        // then
        assertFalse(shouldPerform)
    }

    @Test
    fun bottomNavigationView_OnNavigationToTopLevelDestination_BecomesVisible() {
        // given
        val observer: Observer<Boolean> = mock()
        mainViewModel.bottomNavigationViewVisible.observeForever(observer)

        // when
        mainViewModel.onNavigationDestinationChanged(Destination.DASHBOARD)

        // then
        verify(observer).onChanged(true)
    }

    @Test
    fun bottomNavigationView_OnNavigationToNotTopLevelDestination_BecomesInvisible() {
        // given
        val observer: Observer<Boolean> = mock()
        mainViewModel.bottomNavigationViewVisible.observeForever(observer)

        // when
        mainViewModel.onNavigationDestinationChanged(Destination.OTHER)

        // then
        verify(observer).onChanged(false)
    }

    @Test
    fun appBarLayout_OnNavigationToTopLevelDestination_BecomesExpanded() {
        // given
        val observer: Observer<Boolean> = mock()
        mainViewModel.appBarLayoutExpanded.observeForever(observer)

        // when
        mainViewModel.onNavigationDestinationChanged(Destination.DASHBOARD)

        // then
        verify(observer).onChanged(true)
    }

    @Test
    fun onBack_OnLoginScreen_DoesNothing() {
        // given
        mainViewModel.onNavigationDestinationChanged(Destination.LOGIN)

        // when
        mainViewModel.onBackPressed()

        // then
        verify(closeAppObserver, never()).onChanged(any())
        verify(navigateToDestinationObserver, never()).onChanged(any())
        verify(navigateBackObserver, never()).onChanged(any())
    }

    @Test
    fun onBack_OnDashboardScreen_ClosesApp() {
        // given
        mainViewModel.onNavigationDestinationChanged(Destination.DASHBOARD)

        // when
        mainViewModel.onBackPressed()

        // then
        verify(navigateToDestinationObserver, never()).onChanged(any())
        verify(navigateBackObserver, never()).onChanged(any())
        verify(closeAppObserver).onChanged(any())
    }

    @Test
    fun onBack_OnTopLevelDestinationOtherThanDashboard_NavigatesToDashboard() {
        // given
        mainViewModel.onNavigationDestinationChanged(Destination.SCHEDULE)

        // when
        mainViewModel.onBackPressed()

        // then
        verify(closeAppObserver, never()).onChanged(any())
        verify(navigateBackObserver, never()).onChanged(any())
        verify(navigateToDestinationObserver).onChanged(capture(destinationEventArgumentCaptor))
        assertEquals(Destination.DASHBOARD, destinationEventArgumentCaptor.value.peekContent())
    }

    @Test
    fun onBack_NotOnTopLevelDestination_NavigatesBack() {
        // given
        mainViewModel.onNavigationDestinationChanged(Destination.OTHER)

        // when
        mainViewModel.onBackPressed()

        // then
        verify(closeAppObserver, never()).onChanged(any())
        verify(navigateToDestinationObserver, never()).onChanged(any())
        verify(navigateBackObserver).onChanged(any())
    }
}