package ca.etsmtl.etsmobile3.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ca.etsmtl.etsmobile3.R
import ca.etsmtl.etsmobile3.presentation.home.HomeFragment
import ca.etsmtl.etsmobile3.presentation.profile.ProfileFragment
import ca.etsmtl.etsmobile3.presentation.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : AppCompatActivity() {

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

        goToFragment(navigation.menu.getItem(0))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        setUpToolbar(navigation.menu.findItem(navigation.selectedItemId))
    }

    private fun setUpToolbar(navigationItem: MenuItem) {
        title = navigationItem.title
    }

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

    private fun getNewFragment(selectedItemId: Int) : Fragment? {
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

    override fun onBackPressed() {
        val seletedItemId = navigation.selectedItemId

        if (R.id.navigation_home != seletedItemId) {
            val homeMenuItem = navigation.menu.findItem(R.id.navigation_home)
            homeMenuItem.isChecked = true
            onNavigationItemSelectedListener.onNavigationItemSelected(homeMenuItem)
        }
    }
}
