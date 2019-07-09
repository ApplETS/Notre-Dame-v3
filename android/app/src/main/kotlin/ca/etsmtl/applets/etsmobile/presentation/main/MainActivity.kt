package ca.etsmtl.applets.etsmobile.presentation.main

import android.content.IntentFilter
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.getColorCompat
import ca.etsmtl.applets.etsmobile.extension.setVisible
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject
import ca.etsmtl.applets.etsmobile.util.BroadCastReceiver
/**
 * A screen which displays a bottom navigation view and wrapper for fragment. The user can
 * select items on the bottom navigation view to switch between fragments.
 *
 * Created by Sonphil on 24-02-18.
 */

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var broadCastReciever : BroadCastReceiver= BroadCastReceiver();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupBottomNavigation()
        subscribeUI()
       var intentFilter : IntentFilter= IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadCastReciever,intentFilter)
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
            val navController = findNavController(R.id.fragmentNavHostMain)

            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                if (mainViewModel.shouldPerformBottomNavigationViewAction()) {
                    NavigationUI.onNavDestinationSelected(item, navController)
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
        val navController = findNavController(R.id.fragmentNavHostMain)
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
            findNavController(R.id.fragmentNavHostMain).navigate(it.toDestinationId())
        })

        mainViewModel.appBarLayoutExpanded.observe(this, Observer { expanded ->
            appBarLayout.setExpanded(expanded, true)
        })

        mainViewModel.bottomNavigationViewVisible.observe(this, Observer { visible ->
            val duration: Long = if (visible) 200 else 0

            bottomNavigationView?.setVisible(visible, duration)
        })

        mainViewModel.closeApp.observe(this, EventObserver {
            finishAffinity()
            unregisterReceiver(broadCastReciever)
        })

        mainViewModel.navigateBack.observe(this, EventObserver {
            super.onBackPressed()
        })
    }
}
