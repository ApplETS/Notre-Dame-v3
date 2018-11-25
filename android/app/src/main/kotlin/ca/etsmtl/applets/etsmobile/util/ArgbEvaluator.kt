package ca.etsmtl.applets.etsmobile.util

import android.animation.ArgbEvaluator
import android.content.Context
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 20-09-18.
 */

fun ArgbEvaluator.gradePercentageColor(context: Context, gradePercentage: Float): Int {
    val startColor: Int
    val endColor: Int
    var colorProportion = gradePercentage
    when (gradePercentage) {
        in 0 until Grades.PASSING_GRADE -> {
            startColor = context.getColorCompat(R.color.failureGradeMinColor)
            endColor = context.getColorCompat(R.color.failureGradeMaxColor)
            colorProportion /= Grades.PASSING_GRADE
        }
        in Grades.PASSING_GRADE until Grades.MIN_GOOD_GRADE -> {
            startColor = context.getColorCompat(R.color.passingGradeColor)
            endColor = context.getColorCompat(R.color.goodGradeMinColor)
            colorProportion -= Grades.PASSING_GRADE
            colorProportion /= Grades.MIN_GOOD_GRADE - Grades.PASSING_GRADE
        }
        else -> {
            startColor = context.getColorCompat(R.color.goodGradeMinColor)
            endColor = context.getColorCompat(R.color.goodGradeMaxColor)

            if (colorProportion >= Grades.MAX_GRADE) {
                colorProportion = 1f
            } else {
                colorProportion -= Grades.MIN_GOOD_GRADE
                colorProportion /= Grades.MAX_GRADE - Grades.MIN_GOOD_GRADE
            }
        }
    }

    return evaluate(colorProportion, startColor, endColor) as Int
}

object Grades {
    const val PASSING_GRADE = 50
    const val MIN_GOOD_GRADE = 80
    const val MAX_GRADE = 100
}