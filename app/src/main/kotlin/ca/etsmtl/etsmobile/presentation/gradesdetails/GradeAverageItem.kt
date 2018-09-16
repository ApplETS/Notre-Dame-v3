package ca.etsmtl.etsmobile.presentation.gradesdetails

import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.util.replaceCommaAndParseToFloat
import com.moos.library.CircleProgressView
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_grade_average.progressViewAverage
import kotlinx.android.synthetic.main.item_grade_average.progressViewGrade
import kotlinx.android.synthetic.main.item_grade_average.tvAverage
import kotlinx.android.synthetic.main.item_grade_average.tvGrade
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
        with (viewHolder) {
            tvRating.text = rating

            tvGrade.apply {
                text = String.format(
                        context.getString(R.string.text_grade_with_percentage),
                        grade,
                        gradeOn,
                        gradePercentage
                )
            }
            setCircleProgressViewProgress(progressViewGrade, gradePercentage, !animatedProgress)

            tvAverage.apply {
                text = String.format(
                        context.getString(R.string.text_grade_with_percentage),
                        average,
                        gradeOn,
                        averagePercentage
                )
            }
            setCircleProgressViewProgress(progressViewAverage, average, !animatedProgress)

            animatedProgress = true
        }
    }

    override fun getLayout() = R.layout.item_grade_average

    private fun setCircleProgressViewProgress(
        circleProgressView: CircleProgressView,
        progress: String?,
        animate: Boolean
    ) {
        progress?.let {
            with(circleProgressView) {
                val progressFloat = it.replaceCommaAndParseToFloat()
                setEndProgress(progressFloat)

                if (animate) {
                    startProgressAnimation()
                } else {
                    this.progress = progressFloat
                }
            }
        }
    }
}