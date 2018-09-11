package ca.etsmtl.etsmobile.presentation.gradesdetails

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.util.show
import ca.etsmtl.repository.data.model.Evaluation
import com.moos.library.CircleProgressView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_evaluation_header.arrow
import kotlinx.android.synthetic.main.item_evaluation_header.btnIgnoredEvaluation
import kotlinx.android.synthetic.main.item_evaluation_header.progressViewGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvIgnoredEvaluation
import kotlinx.android.synthetic.main.item_evaluation_header.tvName
import kotlinx.android.synthetic.main.item_evaluation_header.tvWeight

/**
 * Created by Sonphil on 05-09-18.
 */
class EvaluationHeaderItem(private val evaluation: Evaluation) : Item(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup
    private val rotateArrowToTop by lazy {
        RotateAnimation(
            0f, -180f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 300
            fillAfter = true
        }
    }
    private val rotateArrowToBottom by lazy {
        RotateAnimation(
                -180f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 300
            fillAfter = true
        }
    }
    private var ignoredEvaluationDialog: AlertDialog? = null

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

                setEndProgress(grade)
                setProgressViewUpdateListener(object : CircleProgressView.CircleProgressUpdateListener {
                    override fun onCircleProgressFinished(view: View?) {}

                    override fun onCircleProgressStart(view: View?) {}

                    override fun onCircleProgressUpdate(view: View?, progress: Float) {
                        val color = progressColor(context, progress)
                        setStartColor(color)
                        setEndColor(color)
                    }
                })
                startProgressAnimation()
            }

            tvIgnoredEvaluation.show(evaluation.ignoreDuCalcul)
            btnIgnoredEvaluation.show(evaluation.ignoreDuCalcul)
            if (evaluation.ignoreDuCalcul) {
                with (View.OnClickListener {
                    showIgnoredEvaluationDialog(tvIgnoredEvaluation.context)
                }) {
                    tvIgnoredEvaluation.setOnClickListener(this)
                    btnIgnoredEvaluation.setOnClickListener(this)
                }
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

    private fun progressColor(context: Context, grade: Float) = ContextCompat.getColor(
            context,
            when {
                grade < 50 -> R.color.colorPrimary
                grade in 50..84 -> R.color.material_yellow_600
                else -> R.color.material_green_600
            }
    )

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    private fun showIgnoredEvaluationDialog(context: Context) {
        if (ignoredEvaluationDialog == null) {
            ignoredEvaluationDialog = AlertDialog.Builder(context)
                    .setTitle(R.string.title_ignored_evaluation)
                    .setMessage(R.string.text_ignored_evaluation)
                    .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .create()
        }

        ignoredEvaluationDialog?.show()
    }
}
