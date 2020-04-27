package ca.etsmtl.applets.etsmobile.presentation.whatsnew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.synthetic.main.fragment_whats_new.*
import java.util.*


class WhatsNewFragment : Fragment() {
    private var mWhatsNewData = ArrayList<WhatsNewObject>()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_whats_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewListener()
    }

    private fun setViewListener() {
        btnCloseWhatsNew.setOnClickListener {
            findNavController().navigate(WhatsNewFragmentDirections.actionFragmentWhatsNewToFragmentSplash())
        }
    }

    private fun setRecyclerView() {

        rvWhatsNew.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = WhatsNewAdapter(mWhatsNewData)
        }
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvWhatsNew)
        initializeData()
    }
    private fun initializeData() {
        // Get the resources from the XML file.
        val title = arrayOf("Whats1", "Whats2", "Whats3")
        val desc = arrayOf("changes1", "changes2", "changes3")
        val version = arrayOf("123.21", "122134", "234.324.436")
        // Clear the existing data (to avoid duplication).
        mWhatsNewData.clear()

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for (i in title.indices) {
            mWhatsNewData.add(WhatsNewObject(title[i], desc[i], version[i]))
        }

        // Notify the adapter of the change.
        rvWhatsNew!!.adapter?.notifyDataSetChanged()
    }
}




