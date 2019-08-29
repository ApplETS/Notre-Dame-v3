package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.app.Activity
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavController
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.student.StudentFragmentDirections
import model.Cours

/**
 * Created by Sonphil on 28-08-19.
 */

fun NavController.navigateToGradesDetails(
    activity: Activity,
    toolbar: View,
    contentView: View,
    cours: Cours
) {
    val action = StudentFragmentDirections.actionToActivityGradesDetails(cours)
    val toolbarPair = Pair.create(
        toolbar,
        activity.getString(R.string.transition_grades_details_toolbar)
    )
    val contentPair = Pair.create(
        contentView,
        activity.getString(R.string.transition_grades_details_content)
    )
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
        activity, toolbarPair, contentPair
    )
    val extras = ActivityNavigatorExtras(options)

    navigate(action, extras)
}