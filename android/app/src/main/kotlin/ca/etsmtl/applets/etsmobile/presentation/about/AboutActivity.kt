package ca.etsmtl.applets.etsmobile.presentation.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * Created by Sonphil on 01-07-18.
 */

class AboutActivity : BaseActivity() {

    companion object {
        private const val EXTRA_TRANSITION_NAME = "ExtraTransitionName"

        /**
         * Starts the activity without a shared element transition animation
         */
        fun start(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }

        /**
         * Starts the activity with a shared element transition animation
         */
        fun start(activity: AppCompatActivity, sharedElement: Pair<View, String>) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement)
            activity.startActivity(Intent(activity, AboutActivity::class.java).apply {
                putExtra(EXTRA_TRANSITION_NAME, sharedElement.second)
            }, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportPostponeEnterTransition()

        setContentView(R.layout.activity_about)

        setUpToolbar()

        if (savedInstanceState == null)
            addAboutFragment()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitle(R.string.more_item_label_about_applets)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addAboutFragment() {
        val fragment = AboutFragment.newInstance(intent.getStringExtra(EXTRA_TRANSITION_NAME))

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.container, fragment, AboutFragment.TAG)
            commit()
        }
    }
}
