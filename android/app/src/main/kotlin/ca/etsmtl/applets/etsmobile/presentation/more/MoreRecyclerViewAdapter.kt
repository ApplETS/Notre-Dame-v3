package ca.etsmtl.applets.etsmobile.presentation.more

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_more_item.iVMoreItemIcon
import kotlinx.android.synthetic.main.fragment_more_item.textViewMoreItemLabel

class MoreRecyclerViewAdapter(
    private val items: List<MoreItem>,
    private val itemClickListener: OnItemClickListener?
)
    : RecyclerView.Adapter<MoreRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_more_item, parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: MoreItem = items[position]

        holder.iconImageView.setImageResource(item.iconId)
        holder.labelTextView.text = item.label

        itemClickListener?.let {
            holder.containerView.setOnClickListener {
                itemClickListener.onItemClick(position, holder)
            }
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val iconImageView: ImageView = iVMoreItemIcon
        val labelTextView: TextView = textViewMoreItemLabel
    }

    /**
     * Interface definition for a callback to be invoked when an item of the recycler view is clicked
     */
    interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item of the recycler view is clicked
         *
         * @param index position of the clicked view
         * @param holder the view holder of the clicked item
         */
        fun onItemClick(index: Int, holder: ViewHolder)
    }
}
