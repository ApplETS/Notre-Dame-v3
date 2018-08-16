package ca.etsmtl.etsmobile.presentation.grades

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.BaseActivity
import ca.etsmtl.repository.data.model.Cours
import kotlinx.android.synthetic.main.activity_grades_details.containerGradesDetails
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsActivity : BaseActivity() {
    companion object {
        private const val EXTRA_TRANSITION_NAME = "ExtraTransitionName"
        private const val EXTRA_COURS = "ExtraCours"

        fun start(activity: AppCompatActivity, sharedElement: Pair<View, String>, cours: Cours) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement)
            activity.startActivity(Intent(activity, GradesDetailsActivity::class.java).apply {
                putExtra(EXTRA_TRANSITION_NAME, sharedElement.second)
                putExtra(EXTRA_COURS, cours)
            }, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grades_details)

        setUpToolbar()

        with (intent?.extras) {
            containerGradesDetails.transitionName = this?.getString(EXTRA_TRANSITION_NAME)

            if (savedInstanceState == null) {
                with (this?.getParcelable(EXTRA_COURS) as Cours) {
                    addFragment(this)
                }
            }
            with (this?.getParcelable(EXTRA_COURS) as Cours) {
                if (savedInstanceState == null) {
                    addFragment(this)
                }

                supportActionBar?.title = this.sigle
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFragment(cours: Cours) {
        val fragment = GradesDetailsFragment.newInstance(cours)

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.containerGradesDetails, fragment, GradesDetailsFragment.TAG)
            commit()
        }
    }
}