package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.rotate
import ca.etsmtl.applets.etsmobile.util.setGradePercentageColor
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
import model.Evaluation

/**
 * Created by Sonphil on 05-09-18.
 */
class EvaluationHeaderItem(private val evaluation: Evaluation) : Item(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup
    private var animatedProgress = false
    private var ignoredEvaluationDialog: AlertDialog? = null

    override fun getLayout() = R.layout.item_evaluation_header

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
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

                grade = grade.coerceIn(0f, 100f)

                setEndProgress(grade)

                if (animatedProgress) {
                    progress = grade
                    setGradePercentageColor(grade)
                } else {
                    setProgressViewUpdateListener(object : CircleProgressView.CircleProgressUpdateListener {
                        override fun onCircleProgressFinished(view: View) {}

                        override fun onCircleProgressStart(view: View) {}

                        override fun onCircleProgressUpdate(view: View, progress: Float) {
                            setGradePercentageColor(progress)
                        }
                    })
                    startProgressAnimation()
                    animatedProgress = true
                }
            }

            tvIgnoredEvaluation.isVisible = evaluation.ignoreDuCalcul
            btnIgnoredEvaluation.isVisible = evaluation.ignoreDuCalcul
            if (evaluation.ignoreDuCalcul) {
                with(View.OnClickListener {
                    showIgnoredEvaluationDialog(tvIgnoredEvaluation.context)
                }) {
                    tvIgnoredEvaluation.setOnClickListener(this)
                    btnIgnoredEvaluation.setOnClickListener(this)
                }
            }

            if (expandableGroup.isExpanded) {
                arrow.rotate(0f, -180f, duration = 0)
            }

            itemView.setOnClickListener {
                expandableGroup.onToggleExpanded()

                when {
                    expandableGroup.isExpanded -> arrow.rotate(0f, -180f)
                    else -> arrow.rotate(-180f, 0f)
                }
            }
        }
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
}
