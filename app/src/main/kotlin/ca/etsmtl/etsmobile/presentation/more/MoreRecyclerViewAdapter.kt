package ca.etsmtl.etsmobile.presentation.more

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ca.etsmtl.etsmobile.R

class MoreRecyclerViewAdapter(
    private val items: List<MoreItem>
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
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val iconImageView: ImageView = view.findViewById(R.id.more_item_icon_iv)
        val labelTextView: TextView = view.findViewById(R.id.more_item_label_text_view)
    }
}
