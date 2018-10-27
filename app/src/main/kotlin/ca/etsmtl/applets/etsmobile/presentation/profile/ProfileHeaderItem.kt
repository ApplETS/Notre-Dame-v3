package ca.etsmtl.applets.etsmobile.presentation.profile

import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.show
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_profile_header.dividerProfile
import kotlinx.android.synthetic.main.item_profile_header.tvTitleProfile

/**
 * Created by Sonphil on 27-10-18.
 */

class ProfileHeaderItem(val title: String) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvTitleProfile.text = title
        viewHolder.dividerProfile.show(position != 0)
    }

    override fun getLayout() = R.layout.item_profile_header

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return title == (other as? ProfileHeaderItem)?.title
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}