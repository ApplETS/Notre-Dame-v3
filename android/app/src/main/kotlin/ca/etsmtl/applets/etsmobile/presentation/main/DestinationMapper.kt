package ca.etsmtl.applets.etsmobile.presentation.main

import androidx.annotation.IdRes
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 11-05-19.
 */

internal fun Int.toDestination() = when (this) {
    R.id.fragmentSplash -> Destination.SPLASH
    R.id.fragmentWhatsNew -> Destination.WHATSNEW
    R.id.fragmentLogin -> Destination.LOGIN
    R.id.fragmentDashboard -> Destination.DASHBOARD
    R.id.fragmentSchedule -> Destination.SCHEDULE
    R.id.fragmentStudent -> Destination.STUDENT
    R.id.fragmentEts -> Destination.ETS
    R.id.fragmentMore -> Destination.MORE
    else -> Destination.OTHER
}

@IdRes
internal fun Destination.toDestinationId(): Int = when (this) {
    Destination.SPLASH -> R.id.fragmentSplash
    Destination.WHATSNEW -> R.id.fragmentWhatsNew
    Destination.LOGIN -> R.id.fragmentLogin
    Destination.DASHBOARD -> R.id.fragmentDashboard
    Destination.SCHEDULE -> R.id.fragmentSchedule
    Destination.STUDENT -> R.id.fragmentStudent
    Destination.ETS -> R.id.fragmentEts
    Destination.MORE -> R.id.fragmentMore
    else -> throw IllegalArgumentException("Invalid destination $name")
}