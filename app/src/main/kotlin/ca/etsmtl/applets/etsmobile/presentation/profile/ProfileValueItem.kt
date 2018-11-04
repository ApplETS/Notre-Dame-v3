package ca.etsmtl.applets.etsmobile.presentation.profile

import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.synthetic.main.item_profile.tvInfoProfileItemLabel
import kotlinx.android.synthetic.main.item_profile.tvProfileItemValue

/**
 * Created by Sonphil on 27-10-18.
 */

class ProfileValueItem(
    val label: String,
    val value: String
) : ProfileItem<ProfileAdapter.ProfileViewHolder.ItemViewHolder> {
    override fun getLayout() = R.layout.item_profile

    override fun bind(viewHolder: ProfileAdapter.ProfileViewHolder.ItemViewHolder, position: Int) {
        with (viewHolder) {
            tvInfoProfileItemLabel.text = label
            tvProfileItemValue.text = value
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || javaClass != other.javaClass) {
            return false
        }

        with (other as ProfileValueItem) {
            return label == other.label && value == other.value
        }
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}