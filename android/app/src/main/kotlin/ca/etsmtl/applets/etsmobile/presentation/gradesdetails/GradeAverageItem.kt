package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.animation.ArgbEvaluator
import android.view.View
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.gradePercentageColor
import ca.etsmtl.applets.etsmobile.util.setGradePercentageColor
import ca.etsmtl.applets.repository.util.replaceCommaAndParseToFloat
import com.moos.library.CircleProgressView
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_grade_average.progressViewCourseAverage
import kotlinx.android.synthetic.main.item_grade_average.progressViewCourseGrade
import kotlinx.android.synthetic.main.item_grade_average.tvAverage
import kotlinx.android.synthetic.main.item_grade_average.tvGrade
import kotlinx.android.synthetic.main.item_grade_average.tvLabelGrade
import kotlinx.android.synthetic.main.item_grade_average.tvRating

/**
 * Created by Sonphil on 08-09-18.
 */
class GradeAverageItem(
    private val rating: String?,
    private val grade: String?,
    private val gradeOn: String?,
    private val gradePercentage: String?,
    private val average: String?,
    private val averagePercentage: String?
) : Item() {
    private var animatedProgress = false

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            tvRating.text = rating

            fun setGrade() {
                tvGrade.apply {
                    text = String.format(
                            context.getString(R.string.text_grade_with_percentage),
                            grade,
                            gradeOn,
                            gradePercentage
                    )
                }

                gradePercentage?.replaceCommaAndParseToFloat()?.let {
                    setCircleProgressViewProgress(progressViewCourseGrade, it, !animatedProgress)
                    progressViewCourseGrade.setGradePercentageColor(it)
                    if (!animatedProgress) {
                        progressViewCourseGrade.setProgressViewUpdateListener(object : CircleProgressView.CircleProgressUpdateListener {
                            override fun onCircleProgressFinished(view: View) { }

                            override fun onCircleProgressStart(view: View) { }

                            override fun onCircleProgressUpdate(view: View, progress: Float) {
                                val progressColor = ArgbEvaluator().gradePercentageColor(view.context, progress)
                                progressViewCourseGrade.setStartColor(progressColor)
                                progressViewCourseGrade.setEndColor(progressColor)
                                tvGrade.setTextColor(progressColor)
                                tvLabelGrade.setTextColor(progressColor)
                            }
                        })
                    } else {
                        val gradeColor = ArgbEvaluator().gradePercentageColor(
                                progressViewCourseAverage.context,
                                it
                        )

                        progressViewCourseGrade.setStartColor(gradeColor)
                        progressViewCourseGrade.setEndColor(gradeColor)
                        tvGrade.setTextColor(gradeColor)
                        tvLabelGrade.setTextColor(gradeColor)
                    }
                }
            }
            setGrade()

            fun setAvarage() {
                tvAverage.apply {
                    text = String.format(
                            context.getString(R.string.text_grade_with_percentage),
                            average,
                            gradeOn,
                            averagePercentage
                    )
                }
                averagePercentage?.replaceCommaAndParseToFloat()?.let {
                    setCircleProgressViewProgress(progressViewCourseAverage, it, !animatedProgress)
                }
            }
            setAvarage()

            animatedProgress = true
        }
    }

    override fun getLayout() = R.layout.item_grade_average

    private fun setCircleProgressViewProgress(
        circleProgressView: CircleProgressView,
        progress: Float,
        animate: Boolean
    ) {
        with(circleProgressView) {
            setEndProgress(progress.coerceIn(0f, 100f))

            if (animate) {
                startProgressAnimation()
            } else {
                this.progress = progress
            }
        }
    }
}