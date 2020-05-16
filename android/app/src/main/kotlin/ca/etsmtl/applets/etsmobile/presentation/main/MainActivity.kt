package ca.etsmtl.applets.etsmobile.presentation.main

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.getColorCompat
import ca.etsmtl.applets.etsmobile.extension.setVisible
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import ca.etsmtl.applets.etsmobile.presentation.whatsnew.WhatsNewFragment
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject

/**
 * A screen which displays an app bar, a bottom navigation view and wrapper for fragment. The user
 * can select items on the bottom navigation view to switch between fragments.
 *
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val navController by lazy { findNavController(R.id.fragmentNavHostMain) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupBottomNavigation()
        showWhatsNew()
        subscribeUI()
    }

    private fun showWhatsNew(){
        val whatNewFragment = WhatsNewFragment()
        var fm = supportFragmentManager
        whatNewFragment.show(fm, "fgsdgf")
    }

    private fun setupBottomNavigation() {
        fun removeLabelPadding() {
            val menuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView

            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                val activeLabel = item.findViewById<View>(R.id.largeLabel)

                /*
                 Set padding to zero because it may cause the active label to be truncated on
                 smaller device
                  */
                if (activeLabel is TextView) {
                    activeLabel.setPadding(0, 0, 0, 0)
                }
            }
        }

        fun setupNavigation() {
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                if (mainViewModel.shouldPerformBottomNavigationViewAction()) {
                    val navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                        .build()

                    navController.navigate(item.itemId, null, navOptions)

                    true
                } else {
                    false
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                mainViewModel.onNavigationDestinationChanged(destination.id.toDestination())

                toolbar.navigationIcon?.setColorFilter(
                    getColorCompat(android.R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }

        removeLabelPadding()
        setupNavigation()
    }

    private fun setupActionBar() {
        appBarLayout.setExpanded(false, false)

        val topLevelDestinations = mainViewModel.topLevelDestinations.map {
            it.toDestinationId()
        }.toSet()
        val appBarConfiguration = AppBarConfiguration(topLevelDestinations)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        mainViewModel.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    private fun subscribeUI() {
        mainViewModel.navigateToDestination.observe(this, EventObserver {
            navController.navigate(it.toDestinationId())
        })

        mainViewModel.expandAppBarLayout.observe(this, Observer { expand ->
            appBarLayout.setExpanded(expand)
        })

        mainViewModel.bottomNavigationViewVisible.observe(this, Observer { visible ->
            bottomNavigationView?.setVisible(visible)
        })

        mainViewModel.closeApp.observe(this, EventObserver {
            finishAffinity()
        })

        mainViewModel.navigateBack.observe(this, EventObserver {
            super.onBackPressed()
        })
    }
}
