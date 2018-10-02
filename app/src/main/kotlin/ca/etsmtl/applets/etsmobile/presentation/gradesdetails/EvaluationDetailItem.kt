package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import ca.etsmtl.applets.etsmobile.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_evaluation_detail.tvLabel
import kotlinx.android.synthetic.main.item_evaluation_detail.tvValue

/**
 * Created by Sonphil on 05-09-18.
 */

class EvaluationDetailItem(
    private val label: String,
    private val value: String
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            tvLabel.text = label
            tvValue.text = value
        }
    }

    override fun getLayout() = R.layout.item_evaluation_detail
}