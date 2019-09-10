package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.repository.util.replaceCommaAndParseToFloat
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
                    progressViewCourseGrade.showGradePercentage(
                        it,
                        !animatedProgress,
                        onUpdate = { gradeColor ->
                            tvGrade.setTextColor(gradeColor)
                            tvLabelGrade.setTextColor(gradeColor)
                        }
                    )
                }
            }
            setGrade()

            fun setAverage() {
                tvAverage.apply {
                    text = String.format(
                            context.getString(R.string.text_grade_with_percentage),
                            average,
                            gradeOn,
                            averagePercentage
                    )
                }
                averagePercentage?.replaceCommaAndParseToFloat()?.let {
                    progressViewCourseAverage.showAveragePercentage(it, !animatedProgress)
                }
            }
            setAverage()

            animatedProgress = true
        }
    }

    override fun getLayout() = R.layout.item_grade_average
}