package ca.etsmtl.etsmobile.presentation

import android.support.design.widget.BottomNavigationView
import dagger.android.support.DaggerFragment

abstract class MainFragment : DaggerFragment() {
    /**
     * Called when the back button has been pressed
     *
     * @return True if the listener has consumed the event, false otherwise
     */
    open fun onBackPressed() = false

    /**
     * Uses an animation to show or hide [MainActivity]'s [BottomNavigationView]
     *
     * @param show True if the [BottomNavigationView] should be shown
     */
    fun toggleBottomNavigationView(show: Boolean) {
        (activity as? MainActivity)?.getBottomNavigationView()?.let {
            it.clearAnimation()
            it.animate()
                    .translationY(when (show) {
                        true -> 0f
                        false -> it.height.toFloat()
                    })
                    .setDuration(200)
        }
    }
}