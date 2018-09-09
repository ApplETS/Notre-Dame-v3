package ca.etsmtl.etsmobile.presentation.gradesdetails

import ca.etsmtl.etsmobile.R
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
    private val average: String?
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        with (viewHolder) {
            tvRating.text = rating
            tvGrade.apply {
                text = String.format(
                        context.getString(R.string.text_grade_in_percentage),
                        grade
                )
            }
            setCircleProgressViewProgress(progressViewGrade, grade)
            tvAverage.apply {
                text = String.format(
                        context.getString(R.string.text_grade_in_percentage),
                        average
                )
            }
            setCircleProgressViewProgress(progressViewAverage, average)
        }
    }

    override fun getLayout() = R.layout.item_grade_average

    private fun setCircleProgressViewProgress(circleProgressView: CircleProgressView, progress: String?) {
        progress?.let {
            with(circleProgressView) {
                setEndProgress(it.replace(",", ".").toFloat())
                startProgressAnimation()
            }
        }
    }
}