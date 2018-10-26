package ca.etsmtl.applets.etsmobile.presentation.main

import android.animation.Animator
import com.google.android.material.bottomnavigation.BottomNavigationView
import ca.etsmtl.applets.etsmobile.util.show
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
     * @param duration The animation duration in ms
     */
    fun toggleBottomNavigationView(show: Boolean, duration: Long = 200) {
        (activity as? MainActivity)?.getBottomNavigationView()?.let {
            it.clearAnimation()
            it.animate()
                    .translationY(when (show) {
                        true -> 0f
                        false -> it.height.toFloat()
                    })
                    .setDuration(duration)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animator: Animator) {}

                        override fun onAnimationEnd(animator: Animator) {
                            if (!show) { // Need to hide view
                                // Set visibility to GONE at the end of animation
                                it.show(false)
                            }
                        }

                        override fun onAnimationCancel(animator: Animator) {}

                        override fun onAnimationStart(animator: Animator) {
                            if (show) { // Need to reveal view
                                // Set visibility to VISIBLE at the beginning of animation
                                it.show(true)
                            }
                        }
                    })
        }
    }
}