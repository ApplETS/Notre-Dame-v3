package ca.etsmtl.applets.etsmobile.presentation.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
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

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_dashboard,
                R.id.navigation_schedule,
                R.id.navigation_student,
                R.id.navigation_ets,
                R.id.navigation_more
        ))
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentNavHostMain).navigateUp()
    }
}
