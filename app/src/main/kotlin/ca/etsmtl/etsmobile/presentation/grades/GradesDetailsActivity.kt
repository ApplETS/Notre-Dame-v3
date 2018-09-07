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
import ca.etsmtl.etsmobile.presentation.gradesdetails.GradesDetailsFragment
import ca.etsmtl.etsmobile.util.show
import ca.etsmtl.repository.data.model.Cours
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsActivity : BaseActivity() {
    companion object {
        private const val EXTRA_COURS = "ExtraCours"

        /**
         * Starts [GradesDetailsActivity] with a shared element transition
         *
         * @param activity The host activity
         * @param content View used to for the shared element transition of the background
         * @param toolBar View used for the shared element transition of the toolbar
         * @param cours The course selected by the user
         */
        fun start(activity: AppCompatActivity, content: View, toolBar: View, cours: Cours) {
            val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            activity,
                            Pair.create(content, activity.getString(R.string
                                    .transition_grades_details_content)),
                            Pair.create(toolBar, activity.getString(R.string
                                    .transition_grades_details_toolbar))
                    )
            activity.startActivity(Intent(activity, GradesDetailsActivity::class.java).apply {
                putExtra(EXTRA_COURS, cours)
            }, options.toBundle())
        }

        /**
         * Starts [GradesDetailsActivity] without a shared element transition
         *
         * @param activity
         * @param cours The course selected by the user
         */
        fun start(activity: AppCompatActivity, cours: Cours) {
            activity.startActivity(Intent(activity, GradesDetailsActivity::class.java).apply {
                putExtra(EXTRA_COURS, cours)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grades_details)

        setUpToolbar()

        with (intent?.extras) {
            with (this?.getParcelable(EXTRA_COURS) as Cours) {
                if (savedInstanceState == null) {
                    addFragment(this)
                }

                supportActionBar?.let {
                    it.title = this.sigle
                    it.subtitle = this.titreCours
                }
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
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

    override fun onBackPressed() {
        // Don't show the toolbar's content during the shared element transition
        toolbar.show(false)

        super.onBackPressed()
    }
}