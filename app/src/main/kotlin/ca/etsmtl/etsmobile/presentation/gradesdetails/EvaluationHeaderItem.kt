package ca.etsmtl.etsmobile.presentation.gradesdetails

import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.Evaluation
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_evaluation_header.tvGrade
import kotlinx.android.synthetic.main.item_evaluation_header.tvName
import kotlinx.android.synthetic.main.item_evaluation_header.tvWeight

/**
 * Created by Sonphil on 05-09-18.
 */
class EvaluationHeaderItem(private val evaluation: Evaluation) : Item(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup

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
                        context.getString(R.string.text_grade_with_percentage),
                        evaluation.note,
                        evaluation.corrigeSur,
                        evaluation.notePourcentage
                ) // TODO: Replace with graph
            }
            itemView.setOnClickListener { expandableGroup.onToggleExpanded() }
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}
