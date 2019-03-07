package ca.etsmtl.applets.etsmobile.extension

import android.animation.ArgbEvaluator
import com.moos.library.CircleProgressView

/**
 * Created by Sonphil on 20-09-18.
 */

fun CircleProgressView.setGradePercentageColor(gradePercentage: Float) {
    val color = ArgbEvaluator().gradePercentageColor(context, gradePercentage)
    setStartColor(color)
    setEndColor(color)
}