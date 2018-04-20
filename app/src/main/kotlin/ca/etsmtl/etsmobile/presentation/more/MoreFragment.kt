package ca.etsmtl.etsmobile.presentation.more

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import java.util.*

class MoreFragment : Fragment() {

    companion object {
        fun newInstance() = MoreFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_more_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                adapter = MoreRecyclerViewAdapter(itemsList())
            }

            view.setHasFixedSize(true)
        }
        return view
    }

    private fun itemsList(): List<MoreItem> {
        val moreItems = ArrayList<MoreItem>()
        val icons = resources.obtainTypedArray(R.array.more_items_icons)
        val labels = resources.getStringArray(R.array.more_items_labels)

        labels.forEachIndexed { index, label ->
            moreItems.add(MoreItem(icons.getResourceId(index,
                    R.drawable.ic_info_outline_white_24dp), label))
        }

        icons.recycle()

        return moreItems
    }
}
