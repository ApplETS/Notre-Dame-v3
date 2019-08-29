package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.graphics.Color
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.transition.doOnStart
import androidx.core.view.isVisible
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.animateBetweenColors
import ca.etsmtl.applets.etsmobile.extension.toast
import ca.etsmtl.applets.etsmobile.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_grades_details.appBarLayoutGradesDetails
import kotlinx.android.synthetic.main.activity_grades_details.containerTvGradesDetailsSubtitle
import kotlinx.android.synthetic.main.activity_grades_details.toolbar
import kotlinx.android.synthetic.main.activity_grades_details.tvGradesDetailsCourseName
import kotlinx.android.synthetic.main.activity_grades_details.tvGradesDetailsGroup
import model.Cours

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWindow()

        setContentView(R.layout.activity_grades_details)

        setupToolbar()

        val bundle = intent?.extras

        if (bundle == null) {
            toast(R.string.error)
            onBackPressed()
        } else {
            val cours = GradesDetailsActivityArgs.fromBundle(bundle).course

            if (savedInstanceState == null) {
                addFragment(cours)
            }

            supportActionBar?.let {
                it.title = cours.sigle
            }
            tvGradesDetailsCourseName.text = cours.titreCours
            tvGradesDetailsGroup.text = String.format(getString(R.string.text_group), cours.groupe)
        }
    }

    private fun setupWindow() = with(window) {
        window.sharedElementEnterTransition = createTransition(
            R.color.colorPrimary,
            R.color.colorToolbar,
            1000L
        )
        window.sharedElementReturnTransition = createTransition(
            R.color.colorToolbar,
            R.color.colorPrimary,
            resources.getInteger(android.R.integer.config_longAnimTime).toLong()
        )

        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
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
        appBarLayoutGradesDetails.setExpanded(true, true)
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

    /**
     * Returns the enter/exit [Transition]
     *
     * The app's bar color smoothly changes between [startColor] and [endColor].
     *
     * @param startColor The app bar's start color
     * @param endColor The app bar's end color
     * @param duration The app's bar color transition duration in milliseconds
     *
     */
    private fun createTransition(
        @ColorRes startColor: Int,
        @ColorRes endColor: Int,
        duration: Long
    ): Transition = TransitionInflater
        .from(this@GradesDetailsActivity)
        .inflateTransition(R.transition.expand_grade_transition)
        .apply {
            doOnStart {
                animateBetweenColors(startColor, endColor, duration) { updatedColor ->
                    appBarLayoutGradesDetails.setBackgroundColor(updatedColor)
                }
            }
        }
}