package ca.etsmtl.applets.etsmobile.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import javax.inject.Inject

/**
 * Created by Sonphil on 11-05-19.
 */

class MainViewModel @Inject constructor() : ViewModel() {
    /**
     * Destinations shown on the bottom navigation view
     */
    val topLevelDestinations = setOf(
        Destination.DASHBOARD,
        Destination.SCHEDULE,
        Destination.STUDENT,
        Destination.ETS,
        Destination.MORE
    )
    private var currentDestination: Destination = Destination.SPLASH
    private val _navigateToDestination = MutableLiveData<Event<Destination>>()
    val navigateToDestination: LiveData<Event<Destination>> = _navigateToDestination
    var expandAppBarLayout = MutableLiveData<Boolean>()
    var bottomNavigationViewVisible = MutableLiveData<Boolean>()
    private val _closeApp = MutableLiveData<Event<Unit>>()
    val closeApp: LiveData<Event<Unit>> = _closeApp
    private val _navigateBack = MutableLiveData<Event<Unit>>()
    val navigateBack: LiveData<Event<Unit>> = _navigateBack

    /**
     * Should be called when the user click on one of the bottom navigation view's item
     *
     * @return True if the action should be performed
     */
    fun shouldPerformBottomNavigationViewAction(): Boolean {
        return currentDestination != Destination.SPLASH && currentDestination != Destination.LOGIN && currentDestination != Destination.WHATSNEW
    }

    fun onNavigationDestinationChanged(destination: Destination) {
        if ((destination == Destination.LOGIN) or (destination == Destination.WHATSNEW)) {
            bottomNavigationViewVisible.value = false
            expandAppBarLayout.value = false
        } else if (topLevelDestinations.contains(destination)) {
            bottomNavigationViewVisible.value = true
            expandAppBarLayout.value = true
        } else {
            bottomNavigationViewVisible.value = false
            expandAppBarLayout.value = true
        }

        currentDestination = destination
    }

    fun onBackPressed() {
        if (currentDestination != Destination.LOGIN) {
            if (topLevelDestinations.contains(currentDestination)) {
                if (currentDestination == Destination.DASHBOARD) {
                    _closeApp.value = Event(Unit)
                } else {
                    _navigateToDestination.value = Event(Destination.DASHBOARD)
                }
            } else {
                _navigateBack.value = Event(Unit)
            }
        }
    }
}