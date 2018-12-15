package ca.etsmtl.applets.etsmobile.presentation.main

import android.animation.Animator
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import ca.etsmtl.applets.etsmobile.util.getColorCompat
import ca.etsmtl.applets.etsmobile.util.isVisible
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlinx.android.synthetic.main.activity_main.toolbar

/**
 * A screen which displays a bottom navigation view and wrapper for fragment. The user can
 * select items on the bottom navigation view to switch between fragments.
 *
 * This activity is displayed when the is user logged in.
 *
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragmentNavHostMain)

        navigation.setupWithNavController(navController)
        navigation.setOnNavigationItemSelectedListener { item ->
            if (!item.isChecked) {
                NavigationUI.onNavDestinationSelected(item, navController).apply {
                    if (this) {
                        appBarLayout.setExpanded(true, true)
                    }
                }
            } else {
                false
            }
        }

        val topLevelDestinations = setOf(
            R.id.fragmentDashboard,
            R.id.fragmentSchedule,
            R.id.fragmentStudent,
            R.id.fragmentEts,
            R.id.fragmentMore
        )
        val appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (!topLevelDestinations.contains(destination.id)) {
                toolbar.navigationIcon?.setColorFilter(
                    getColorCompat(android.R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
    }

    fun toggleBottomNavigationView(show: Boolean, duration: Long = 200) {
        navigation.let {
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
                            it.isVisible = false
                        }
                    }
                    override fun onAnimationCancel(animator: Animator) {}
                    override fun onAnimationStart(animator: Animator) {
                        if (show) { // Need to reveal view
                            // Set visibility to VISIBLE at the beginning of animation
                            it.isVisible = true
                        }
                    }
                })
        }
    }
}
