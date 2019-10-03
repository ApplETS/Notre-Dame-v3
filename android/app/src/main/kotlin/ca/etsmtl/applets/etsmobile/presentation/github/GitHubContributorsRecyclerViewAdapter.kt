package ca.etsmtl.applets.etsmobile.presentation.github

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.open
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_github_contributor.view.ivGitHubContributorAvatar
import kotlinx.android.synthetic.main.item_github_contributor.view.tvGitHubContributorUserName
import model.GitHubContributor

/**
 * Created by Sonphil on 02-10-19.
 */

class GitHubContributorsRecyclerViewAdapter : RecyclerView.Adapter<GitHubContributorsRecyclerViewAdapter.ViewHolder>() {
    var items: List<GitHubContributor> = emptyList()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_github_contributor,
            parent,
            false
        )
        val viewHolder = ViewHolder(view)

        view.setOnClickListener { view ->
            val item = items[viewHolder.adapterPosition]

            Uri.parse(item.htmlUrl).open(view.context)
        }

        return viewHolder
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contributor = items[position]

        Glide
            .with(holder.itemView)
            .load(contributor.avatarUrl)
            .into(holder.itemView.ivGitHubContributorAvatar)

        holder.itemView.tvGitHubContributorUserName.text = contributor.login
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}