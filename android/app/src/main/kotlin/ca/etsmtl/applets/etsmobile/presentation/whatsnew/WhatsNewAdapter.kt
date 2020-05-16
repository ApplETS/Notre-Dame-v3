package ca.etsmtl.applets.etsmobile.presentation.whatsnew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import data.api.model.WhatsNewItems

class WhatsNewAdapter(whatsNewData: ArrayList<WhatsNewItems>) : RecyclerView.Adapter<WhatsNewAdapter.ViewHolder?>() {

    // Member variables.
    private var mWhatsNewData: ArrayList<WhatsNewItems>? = whatsNewData
    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Member Variables for the TextViews
        private val mTitleText: TextView = itemView.findViewById(R.id.tvTitle)
        private val mInfoText: TextView = itemView.findViewById(R.id.tvDescription)
        private val mVersionText: TextView = itemView.findViewById(R.id.tvVersionNumber)

        fun bindTo(currentWhatsNew: WhatsNewItems) {
            // Populate the textviews with data.
            mTitleText.text = currentWhatsNew.title
            mInfoText.text = currentWhatsNew.description
            mVersionText.text = currentWhatsNew.version
        }
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get current WhatsNew.
        val currentWhatsNew = mWhatsNewData!![position]

        // Populate the textviews with data.
        holder.bindTo(currentWhatsNew)
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    override fun getItemCount(): Int {
        return mWhatsNewData!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_whats_new, parent, false))
    }
}