package ca.etsmtl.applets.etsmobile.presentation.profile

import androidx.annotation.LayoutRes

/**
 * Created by Sonphil on 02-11-18.
 */

interface ProfileItem<VH : ProfileAdapter.ProfileViewHolder> {
    @LayoutRes
    fun getLayout(): Int

    fun bind(viewHolder: VH, position: Int)
}