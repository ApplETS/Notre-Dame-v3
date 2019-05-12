package ca.etsmtl.applets.etsmobile.presentation.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.Event
import javax.inject.Inject

/**
 * Created by Sonphil on 11-05-19.
 */

class MainViewModel @Inject constructor(
    private val context: Context,
    private val prefs: SharedPreferences
) : ViewModel() {
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
    var appBarLayoutExpanded = MutableLiveData<Boolean>()
    var bottomNavigationViewVisible = MutableLiveData<Boolean>()
    private val _closeApp = MutableLiveData<Event<Unit>>()
    val closeApp: LiveData<Event<Unit>> = _closeApp
    private val _navigateBack = MutableLiveData<Event<Unit>>()
    val navigateBack: LiveData<Event<Unit>> = _navigateBack

    fun shouldPerformBottomNavigationViewAction(): Boolean {
        return currentDestination != Destination.SPLASH && currentDestination != Destination.LOGIN
    }

    fun onNavigationDestinationChanged(destination: Destination) {
        if (topLevelDestinations.contains(destination)) {
            bottomNavigationViewVisible.value = true

            appBarLayoutExpanded.value = true
        } else {
            bottomNavigationViewVisible.value = false
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

    fun getDarkThemePreferenceValue(): String? = prefs.getString(
        context.getString(R.string.key_dark_theme_pref),
        context.getString(R.string.default_entry_value_dark_theme_pref)
    )
}