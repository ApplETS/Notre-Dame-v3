package ca.etsmtl.etsmobile.presentation

import dagger.android.support.DaggerFragment

abstract class MainFragment : DaggerFragment() {
    /**
     * Called when the back button has been pressed
     *
     * @return True if the listener has consumed the event, false otherwise
     */
    open fun onBackPressed() = false
}