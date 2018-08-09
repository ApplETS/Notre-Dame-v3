package ca.etsmtl.etsmobile.presentation.more

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.MainFragment
import ca.etsmtl.etsmobile.presentation.more.MoreRecyclerViewAdapter.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_more.progressMore
import kotlinx.android.synthetic.main.fragment_more.recyclerViewMore
import kotlinx.android.synthetic.main.include_toolbar.toolbar
import javax.inject.Inject

class MoreFragment : MainFragment() {

    companion object {
        const val TAG = "MoreFragment"
        fun newInstance() = MoreFragment()
    }

    private val moreViewModel: MoreViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MoreViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_more, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setTitle(R.string.title_more)

        setUpRecyclerView(recyclerViewMore)

        subscribeUI()
    }

    private fun setUpRecyclerView(view: RecyclerView) {
        with(view) {
            val itemsList = moreViewModel.itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList, object : OnItemClickListener {
                override fun onItemClick(index: Int) {
                    moreViewModel.selectItem(index)
                }
            })
        }

        view.setHasFixedSize(true)
    }

    private fun displayLogoutConfirmationDialog(context: Context) {
        val builder = AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)

        builder.setMessage(R.string.prompt_log_out_confirmation)
                .setTitle(getString(R.string.more_item_label_log_out))
                .setPositiveButton(R.string.yes) { dialog, which ->
                    dialog.dismiss()
                    moreViewModel.logout()
                }
                .setNegativeButton(R.string.no) { dialog, which -> dialog.dismiss() }

        builder.create().show()
    }

    private fun subscribeUI() {
        moreViewModel.getDisplayLogoutDialog().observe(this, Observer {
            context?.let { context -> displayLogoutConfirmationDialog(context) }
        })

        moreViewModel.getDisplayMessage().observe(this, Observer { message ->
            message?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        moreViewModel.getActivityToGoTo().observe(this, Observer {
            with(Intent(context, it)) {
                startActivity(this)
            }
        })

        moreViewModel.getLoading().observe(this, Observer {
            it?.let {
                if (it) {
                    recyclerViewMore.visibility = View.GONE
                    progressMore.visibility = View.VISIBLE
                } else {
                    recyclerViewMore.visibility = View.VISIBLE
                    progressMore.visibility = View.GONE
                }
            }
        })
    }
}
