package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.toast
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_grades_details.containerTvGradesDetailsSubtitle
import kotlinx.android.synthetic.main.activity_grades_details.toolbar
import kotlinx.android.synthetic.main.activity_grades_details.tvGradesDetailsCourseName
import kotlinx.android.synthetic.main.activity_grades_details.tvGradesDetailsGroup
import model.Cours

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

        val transition = TransitionInflater.from(this).inflateTransition(R.transition.expand_grade_transition)
        window.sharedElementEnterTransition = transition
        window.sharedElementExitTransition = transition

        setContentView(R.layout.activity_grades_details)

        setupToolbar()

        with(intent?.extras) {
            with(this?.getParcelable(EXTRA_COURS) as? Cours) {
                if (this == null) {
                    toast(R.string.error)
                    onBackPressed()
                } else {
                    if (savedInstanceState == null) {
                        addFragment(this)
                    }

                    supportActionBar?.let {
                        it.title = sigle
                    }
                    tvGradesDetailsCourseName.text = titreCours
                    tvGradesDetailsGroup.text = String.format(getString(R.string.text_group), groupe)
                }
            }
        }
    }

    private fun setupToolbar() {
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
        containerTvGradesDetailsSubtitle.isVisible = false
        toolbar.isVisible = false
        supportFragmentManager.findFragmentByTag(GradesDetailsFragment.TAG)?.let {
            with(supportFragmentManager.beginTransaction()) {
                remove(it)
                commit()
            }
        }

        super.onBackPressed()
    }
}