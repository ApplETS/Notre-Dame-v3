package ca.etsmtl.etsmobile.presentation.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.login.LoginActivity
import ca.etsmtl.etsmobile.presentation.more.MoreRecyclerViewAdapter.OnItemClickListener

class MoreFragment : Fragment() {

    companion object {
        fun newInstance() = MoreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more_list, container, false)

        if (view is RecyclerView)
            setUpRecyclerView(view)

        return view
    }

    private fun setUpRecyclerView(view: RecyclerView) {
        with(view) {
            val itemsList = itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList, object : OnItemClickListener {
                override fun onItemClick(index: Int) {
                    displayLogOutConfirmationDialog(context)
                }
            })
        }

        view.setHasFixedSize(true)
    }

    private fun displayLogOutConfirmationDialog(context: Context) {
        val builder = AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)

        builder.setMessage(R.string.prompt_log_out_confirmation)
                .setTitle(getString(R.string.more_item_label_log_out))
                .setPositiveButton(R.string.yes) { dialog, which -> logOut() }
                .setNegativeButton(R.string.no) { dialog, which -> dialog.dismiss() }

        builder.create().show()
    }

    private fun logOut() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(LoginActivity.LOGGING_OUT_EXTRA, true)
        startActivity(intent)

        // Finish the activity to prevent the user from going back
        activity?.finish()
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
