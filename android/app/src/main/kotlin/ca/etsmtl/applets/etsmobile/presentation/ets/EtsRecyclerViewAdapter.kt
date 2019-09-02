package ca.etsmtl.applets.etsmobile.presentation.ets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_ets.ivEts
import kotlinx.android.synthetic.main.item_ets.tvEts

/**
 * Created by Sonphil on 09-12-18.
 */

class EtsRecyclerViewAdapter(
    private val items: List<EtsItem>
) : RecyclerView.Adapter<EtsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ets, parent, false)
    ).apply {
        itemView.setOnClickListener {
            items[adapterPosition].onItemClickHandler.invoke(adapterPosition)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: EtsItem = items[position]

        with(holder) {
            ivEts.setImageResource(item.iconId)
            if (item.label == null) {
                tvEts.visibility = View.GONE
            } else {
                tvEts.setText(item.label)
                tvEts.visibility = View.VISIBLE
            }
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}