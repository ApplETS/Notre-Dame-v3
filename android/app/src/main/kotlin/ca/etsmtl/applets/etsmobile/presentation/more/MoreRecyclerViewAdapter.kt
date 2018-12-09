package ca.etsmtl.applets.etsmobile.presentation.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_more_item.iVMoreItemIcon
import kotlinx.android.synthetic.main.fragment_more_item.textViewMoreItemLabel

class MoreRecyclerViewAdapter(
    private val items: List<MoreItem>
) : RecyclerView.Adapter<MoreRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_more_item, parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: MoreItem = items[position]

        holder.iconImageView.setImageResource(item.iconId)
        holder.labelTextView.setText(item.label)
        holder.containerView.setOnClickListener {
            item.moreItemClickHandler.invoke(position)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val iconImageView: ImageView = iVMoreItemIcon
        val labelTextView: TextView = textViewMoreItemLabel
    }
}
