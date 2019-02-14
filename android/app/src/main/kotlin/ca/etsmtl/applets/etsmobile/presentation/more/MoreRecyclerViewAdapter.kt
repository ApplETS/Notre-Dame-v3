package ca.etsmtl.applets.etsmobile.presentation.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_more.iVMoreItemIcon
import kotlinx.android.synthetic.main.item_more.textViewMoreItemLabel

class MoreRecyclerViewAdapter(
    private val items: List<MoreItem>
) : RecyclerView.Adapter<MoreRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_more, parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val item: MoreItem = items[position]

            iVMoreItemIcon.setImageResource(item.iconId)
            iVMoreItemIcon.transitionName = iVMoreItemIcon.context.getString(item.label)
            textViewMoreItemLabel.setText(item.label)
            containerView.setOnClickListener {
                item.moreItemClickHandler.invoke(position)
            }
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
