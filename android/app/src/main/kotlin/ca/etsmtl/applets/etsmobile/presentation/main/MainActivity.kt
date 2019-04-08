package ca.etsmtl.applets.etsmobile.presentation.main

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.getColorCompat
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.toolbar

/**
 * A screen which displays a bottom navigation view and wrapper for fragment. The user can
 * select items on the bottom navigation view to switch between fragments.
 *
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : BaseActivity() {

    private val topLevelDestinations = setOf(
        R.id.fragmentDashboard,
        R.id.fragmentSchedule,
        R.id.fragmentStudent,
        R.id.fragmentEts,
        R.id.fragmentMore
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        fun removeLabelPadding() {
            val menuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView

            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                val activeLabel = item.findViewById<View>(R.id.largeLabel)

                if (activeLabel is TextView) {
                    activeLabel.setPadding(0, 0, 0, 0)
                }
            }
        }

        fun setupNavigation() {
            val navController = findNavController(R.id.fragmentNavHostMain)

            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                val currentId = navController.currentDestination?.id

                if (!item.isChecked && currentId != R.id.fragmentSplash && currentId != R.id.fragmentLogin) {
                    NavigationUI.onNavDestinationSelected(item, navController).apply {
                        if (this && currentId != R.id.fragmentStudent) {
                            appBarLayout.setExpanded(true, true)
                        }
                    }
                } else {
                    false
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (!topLevelDestinations.contains(destination.id)) {
                    toolbar.navigationIcon?.setColorFilter(
                        getColorCompat(android.R.color.white),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }
        }

        removeLabelPadding()
        setupNavigation()
    }

    private fun setupActionBar() {
        val navController = findNavController(R.id.fragmentNavHostMain)
        val appBarConfiguration = AppBarConfiguration(topLevelDestinations)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fragmentNavHostMain)
        val currentId = navController.currentDestination?.id

        if (currentId != R.id.fragmentLogin) {
            if (topLevelDestinations.contains(currentId)) {
                if (currentId == R.id.fragmentDashboard) {
                    finishAffinity()
                } else {
                    navController.navigate(R.id.fragmentDashboard)
                }
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }
}
