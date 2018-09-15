package ca.etsmtl.etsmobile.presentation.gradesdetails

import android.animation.ArgbEvaluator
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
    private var dataSet = false
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
        if (dataSet) {
            return
        }

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
                    override fun onCircleProgressFinished(view: View) {}

                    override fun onCircleProgressStart(view: View) {}

                    override fun onCircleProgressUpdate(view: View, progress: Float) {
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

            dataSet = true
        }
    }

    private fun progressColor(context: Context, grade: Float): Int {
        val startColor: Int
        val endColor: Int
        var colorProportion = grade
        when (grade) {
            in 0 until PASSING_GRADE -> {
                startColor = ContextCompat.getColor(context, R.color.failureGradeMinColor)
                endColor = ContextCompat.getColor(context, R.color.failureGradeMaxColor)
                colorProportion /= PASSING_GRADE
            }
            in PASSING_GRADE until MIN_GOOD_GRADE -> {
                startColor = ContextCompat.getColor(context, R.color.passingGradeColor)
                endColor = ContextCompat.getColor(context, R.color.goodGradeMinColor)
                colorProportion -= PASSING_GRADE
                colorProportion /= MIN_GOOD_GRADE - PASSING_GRADE
            }
            else -> {
                startColor = ContextCompat.getColor(context, R.color.goodGradeMinColor)
                endColor = ContextCompat.getColor(context, R.color.goodGradeMaxColor)

                if (colorProportion >= MAX_GRADE) {
                    colorProportion = 1f
                } else {
                    colorProportion -= MIN_GOOD_GRADE
                    colorProportion /= MAX_GRADE - MIN_GOOD_GRADE
                }
            }
        }

        return ArgbEvaluator().evaluate(colorProportion, startColor, endColor) as Int
    }

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

    companion object {
        private const val PASSING_GRADE = 50
        private const val MIN_GOOD_GRADE = 80
        private const val MAX_GRADE = 100
    }
}
