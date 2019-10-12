package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.rotate
import ca.etsmtl.applets.repository.util.replaceCommaAndParseToFloat
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_evaluation_header.arrow
import kotlinx.android.synthetic.main.item_evaluation_header.btnIgnoredEvaluation
import kotlinx.android.synthetic.main.item_evaluation_header.progressViewAverage
import kotlinx.android.synthetic.main.item_evaluation_header.progressViewGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvIgnoredEvaluation
import kotlinx.android.synthetic.main.item_evaluation_header.tvName
import kotlinx.android.synthetic.main.item_evaluation_header.tvWeight
import kotlinx.android.synthetic.main.item_evaluation_header.view.arrow
import model.Evaluation

/**
 * Created by Sonphil on 05-09-18.
 */
class EvaluationHeaderItem(private val evaluation: Evaluation) : Item(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup
    /** True if progress has already been animated (should be done once per evaluation) **/
    private var animatedProgress = false
    private var ignoredEvaluationDialog: AlertDialog? = null

    override fun getLayout() = R.layout.item_evaluation_header

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            bindProgressViews()

            bindTextContent()

            if (expandableGroup.isExpanded) {
                arrow.rotate(0f, -180f, duration = 0)
            }

            itemView.setOnClickListener {
                expandableGroup.onToggleExpanded()

                when {
                    expandableGroup.isExpanded -> itemView.arrow.rotate(0f, -180f)
                    else -> itemView.arrow.rotate(-180f, 0f)
                }
            }
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    private fun showIgnoredEvaluationDialog(context: Context) {
        if (ignoredEvaluationDialog == null) {
            ignoredEvaluationDialog = AlertDialog.Builder(context, R.style.AppTheme_Dialog_Alert)
                .setTitle(R.string.title_ignored_evaluation)
                .setMessage(R.string.text_ignored_evaluation)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .create()
        }

        ignoredEvaluationDialog?.show()
    }

    private fun ViewHolder.bindProgressViews() {
        tvGrade.apply {
            text = String.format(
                context.getString(R.string.text_grade_in_percentage),
                evaluation.notePourcentage
            )
        }

        progressViewGrade.apply {
            val grade = evaluation.notePourcentage?.replaceCommaAndParseToFloat() ?: 0f

            showGradePercentage(grade, !animatedProgress, null)
        }

        progressViewAverage.apply {
            val average = evaluation.moyennePourcentage?.replaceCommaAndParseToFloat() ?: 0f

            showAveragePercentage(average, !animatedProgress)
        }

        animatedProgress = true
    }

    private fun ViewHolder.bindTextContent() {
        tvName.text = evaluation.nom
        tvWeight.apply {
            text = String.format(
                context.getString(R.string.text_weight),
                evaluation.ponderation
            )
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
    }
}
