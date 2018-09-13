package ca.etsmtl.etsmobile.presentation.gradesdetails

import ca.etsmtl.etsmobile.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_grades_details_section_title.tvSectionTitle

/**
 * Created by Sonphil on 12-09-18.
 */

class SectionTitleItem(private val title: String) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvSectionTitle.text = title
    }

    override fun getLayout() = R.layout.item_grades_details_section_title
}