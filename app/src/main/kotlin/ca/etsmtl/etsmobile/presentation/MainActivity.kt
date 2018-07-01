package ca.etsmtl.etsmobile.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.dashboard.DashboardFragment
import ca.etsmtl.etsmobile.presentation.ets.EtsFragment
import ca.etsmtl.etsmobile.presentation.more.MoreFragment
import ca.etsmtl.etsmobile.presentation.schedule.ScheduleFragment
import ca.etsmtl.etsmobile.presentation.student.StudentFragment
import ca.etsmtl.etsmobile.util.disableShiftMode
import kotlinx.android.synthetic.main.activity_main.navigation

/**
 * A screen which displays a bottom navigation view and wrapper for fragment. The user can
 * select items on the bottom navigation view to switch between fragments.
 *
 * This activity is displayed when the is user logged in.
 *
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : BaseActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        goToFragment(item)

        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navigation.disableShiftMode()
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            selectDashboard()
        }
    }

    /**
     * Displays the fragment corresponding to the selected item. The fragment is instantiated if it
     * doesn't exist yet.
     *
     * @param navigationItem the item the user selected in the bottom navigation view
     */
    private fun goToFragment(navigationItem: MenuItem) {
        val fragmentManager = supportFragmentManager
        val fragmentTag: String = navigationItem.itemId.toString()
        var fragment = fragmentManager.findFragmentByTag(fragmentTag)

        if (fragment == null) {
            fragment = getNewFragment(navigationItem.itemId)
        }

        if (fragment != null) {
            with(fragmentManager.beginTransaction()) {
                replace(R.id.content, fragment, fragmentTag)
                addToBackStack(fragmentTag)
                commit()
            }
        }
    }

    /**
     * Creates a new fragment corresponding to the selected item
     *
     * @param selectedItemId the id of the item selected in the bottom navigation view
     * @return the fragment corresponding to the selected item
     */
    private fun getNewFragment(selectedItemId: Int): Fragment? {
        var fragment: Fragment? = null

        when (selectedItemId) {
            R.id.navigation_dashboard -> {
                fragment = DashboardFragment.newInstance()
            }
            R.id.navigation_schedule -> {
                fragment = ScheduleFragment.newInstance()
            }
            R.id.navigation_profile -> {
                fragment = StudentFragment.newInstance()
            }
            R.id.navigation_ets -> {
                fragment = EtsFragment.newInstance()
            }
            R.id.navigation_more -> {
                fragment = MoreFragment.newInstance()
            }
        }

        return fragment
    }

    /**
     * On back pressed, return to the dashboard or close if the dashboard has already been selected
     */
    override fun onBackPressed() {
        val seletedItemId = navigation.selectedItemId

        if (R.id.navigation_dashboard != seletedItemId) {
            selectDashboard()
        } else {
            finishAndRemoveTask()
        }
    }

    private fun selectDashboard() {
        val homeMenuItem = navigation.menu.findItem(R.id.navigation_dashboard)
        homeMenuItem.isChecked = true
        onNavigationItemSelectedListener.onNavigationItemSelected(homeMenuItem)
    }
}
