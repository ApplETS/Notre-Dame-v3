package ca.etsmtl.etsmobile.presentation.gradesdetails

import android.view.animation.Animation
import android.view.animation.RotateAnimation
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.Evaluation
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_evaluation_header.arrow
import kotlinx.android.synthetic.main.item_evaluation_header.progressViewGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvName
import kotlinx.android.synthetic.main.item_evaluation_header.tvWeight

/**
 * Created by Sonphil on 05-09-18.
 */
class EvaluationHeaderItem(private val evaluation: Evaluation) : Item(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup
    private val rotateArrowToTop by lazy {
        RotateAnimation(
            0f, 180f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 300
            fillAfter = true
        }
    }
    private val rotateArrowToBottom by lazy {
        RotateAnimation(
                180f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 300
            fillAfter = true
        }
    }

    override fun getLayout() = R.layout.item_evaluation_header

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with (viewHolder) {
            tvName.text = evaluation.nom
            tvWeight.apply {
                text = String.format(
                        context.getString(R.string.text_weight),
                        evaluation.ponderation
                )
            }
            tvGrade.apply {
                text = String.format(
                        context.getString(R.string.text_grade_in_percentage),
                        evaluation.notePourcentage
                )
            }
            progressViewGrade.apply {
                var grade = evaluation.notePourcentage
                        .replaceFirst(",", ".")
                        .toFloat()

                if (grade > 100) {
                    grade = 100f
                }
                this.setEndProgress(grade)
                this.startProgressAnimation()
            }
            itemView.setOnClickListener {
                expandableGroup.onToggleExpanded()

                when {
                    expandableGroup.isExpanded -> arrow.startAnimation(rotateArrowToTop)
                    else -> arrow.startAnimation(rotateArrowToBottom)
                }
            }
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}
