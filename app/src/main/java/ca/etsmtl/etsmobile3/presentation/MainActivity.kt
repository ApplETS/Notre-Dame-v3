package ca.etsmtl.etsmobile3.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ca.etsmtl.etsmobile3.R
import ca.etsmtl.etsmobile3.presentation.home.HomeFragment
import ca.etsmtl.etsmobile3.presentation.profile.ProfileFragment
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
            when (navigationItem.itemId) {
                R.id.navigation_home -> {
                    fragment = HomeFragment.newInstance()
                }
                R.id.navigation_schedule -> {
                }
                R.id.navigation_profile -> {
                    fragment = ProfileFragment.newInstance()
                }
                R.id.navigation_ets -> {
                }
            }
        }

        if (fragment != null) {
            fragmentTransaction.replace(content.id, fragment, fragmentTag)
            fragmentTransaction.commit()
        }
    }
}
