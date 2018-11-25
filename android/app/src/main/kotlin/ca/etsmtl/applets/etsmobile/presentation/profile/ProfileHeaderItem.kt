package ca.etsmtl.applets.etsmobile.presentation.profile

import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.show
import kotlinx.android.synthetic.main.item_profile_header.dividerProfile
import kotlinx.android.synthetic.main.item_profile_header.tvTitleProfile

/**
 * Created by Sonphil on 27-10-18.
 */

data class ProfileHeaderItem(val title: String) : ProfileItem<ProfileAdapter.ProfileViewHolder.HeaderViewHolder> {
    override fun getLayout() = R.layout.item_profile_header

    override fun bind(viewHolder: ProfileAdapter.ProfileViewHolder.HeaderViewHolder, position: Int) {
        viewHolder.tvTitleProfile.text = title
        viewHolder.dividerProfile.show(position != 0)
    }
}