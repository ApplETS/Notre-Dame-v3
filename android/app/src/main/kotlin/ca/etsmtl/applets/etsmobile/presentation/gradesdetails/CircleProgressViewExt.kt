package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.animation.ArgbEvaluator
import android.view.View
import androidx.annotation.ColorInt
import com.moos.library.CircleProgressView

/**
 * Created by Sonphil on 20-09-18.
 */

@ColorInt
private fun CircleProgressView.setGradePercentageColor(gradePercentage: Float): Int {
    val color = ArgbEvaluator().gradePercentageColor(context, gradePercentage)

    setStartColor(color)
    setEndColor(color)

    return color
}

private fun CircleProgressView.animateGradePercentageProgress(
    onUpdate: ((currentProgressColor: Int) -> Unit)?
) {
    setProgressViewUpdateListener(object : CircleProgressView.CircleProgressUpdateListener {
        override fun onCircleProgressFinished(view: View) {
        }

        override fun onCircleProgressStart(view: View) { }

        override fun onCircleProgressUpdate(view: View, progress: Float) {
            val progressColor = setGradePercentageColor(progress)

            if (onUpdate != null) {
                onUpdate(progressColor)
            }
        }
    })

    startProgressAnimation()
}

fun CircleProgressView.showGradePercentage(
    gradePercentage: Float,
    animated: Boolean,
    onUpdate: ((currentProgressColor: Int) -> Unit)?
) {
    setEndProgress(gradePercentage.coerceIn(0f, 100f))

    if (animated) {
        animateGradePercentageProgress(onUpdate)
    } else {
        this.progress = progress
        val progressColor = setGradePercentageColor(gradePercentage)

        if (onUpdate != null) {
            onUpdate(progressColor)
        }
    }
}

fun CircleProgressView.showAveragePercentage(
    averagePercentage: Float,
    animated: Boolean
) {
    setEndProgress(averagePercentage.coerceIn(0f, 100f))

    if (animated) {
        startProgressAnimation()
    } else {
        this.progress = progress
    }
}