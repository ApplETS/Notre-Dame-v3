package ca.etsmtl.applets.etsmobile.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by Sonphil on 02-11-18.
 */

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
    var items: List<ProfileItem<out ProfileViewHolder>> = emptyList()
        set(value) {
            val diffCallback = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = field[oldItemPosition]
                    val newItem = value[newItemPosition]

                    return oldItem == newItem
                }

                override fun getOldListSize() = field.count()

                override fun getNewListSize() = value.count()

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = field[oldItemPosition]
                    val newItem = value[newItemPosition]

                    return oldItem == newItem
                }
            }

            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val containerView = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)

        return if (viewType == R.layout.item_profile_header) {
            ProfileViewHolder.HeaderViewHolder(containerView)
        } else {
            ProfileViewHolder.ItemViewHolder(containerView)
        }
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val item = items[position]

        if (item is ProfileValueItem) {
            item.bind(holder as ProfileViewHolder.ItemViewHolder, position)
        } else if (item is ProfileHeaderItem) {
            item.bind(holder as ProfileViewHolder.HeaderViewHolder, position)
        }
    }

    override fun getItemViewType(position: Int) = items[position].getLayout()

    sealed class ProfileViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        class ItemViewHolder(override val containerView: View) : ProfileViewHolder(containerView)

        class HeaderViewHolder(override val containerView: View) : ProfileViewHolder(containerView)
    }
}