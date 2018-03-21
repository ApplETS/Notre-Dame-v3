package ca.etsmtl.etsmobile.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.home.HomeFragment
import ca.etsmtl.etsmobile.presentation.profile.ProfileFragment
import ca.etsmtl.etsmobile.presentation.schedule.ScheduleFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A screen which displays a bottom navigation view and wrapper for fragment. The user can
 * select items on the bottom navigation view to switch between fragments.
 *
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : DaggerAppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        setUpToolbar(item)

        goToFragment(item)

        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        title = getString(R.string.title_home)

        // Go to the first fragment only on the first run
        if (savedInstanceState == null)
            goToFragment(navigation.menu.getItem(0))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        setUpToolbar(navigation.menu.findItem(navigation.selectedItemId))
    }

    private fun setUpToolbar(navigationItem: MenuItem) {
        title = navigationItem.title
    }

    /**
     * Displays the fragment corresponding to the selected item. The fragment is instantiated if it
     * doesn't exist yet.
     *
     * @param navigationItem the item the user selected in the bottom navigation view
     */
    private fun goToFragment(navigationItem: MenuItem) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentTag: String = navigationItem.itemId.toString()
        var fragment = fragmentManager.findFragmentByTag(fragmentTag)

        if (fragment == null) {
            fragment = getNewFragment(navigationItem.itemId)
        }

        if (fragment != null) {
            fragmentTransaction.replace(content.id, fragment, fragmentTag)
            fragmentTransaction.commit()
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
            R.id.navigation_home -> {
                fragment = HomeFragment.newInstance()
            }
            R.id.navigation_schedule -> {
                fragment = ScheduleFragment.newInstance()
            }
            R.id.navigation_profile -> {
                fragment = ProfileFragment.newInstance()
            }
            R.id.navigation_ets -> {
            }
            R.id.navigation_more -> {
            }
        }

        return fragment
    }

    /**
     * On back pressed, return to the home screen
     */
    override fun onBackPressed() {
        val seletedItemId = navigation.selectedItemId

        if (R.id.navigation_home != seletedItemId) {
            val homeMenuItem = navigation.menu.findItem(R.id.navigation_home)
            homeMenuItem.isChecked = true
            onNavigationItemSelectedListener.onNavigationItemSelected(homeMenuItem)
        }
    }
}
