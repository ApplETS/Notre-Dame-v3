package ca.etsmtl.applets.etsmobile.presentation.more

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Pair
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.applets.etsmobile.presentation.main.MainFragment
import ca.etsmtl.applets.etsmobile.presentation.more.MoreRecyclerViewAdapter.OnItemClickListener
import ca.etsmtl.applets.etsmobile.util.EventObserver
import kotlinx.android.synthetic.main.fragment_more.progressMore
import kotlinx.android.synthetic.main.fragment_more.recyclerViewMore
import kotlinx.android.synthetic.main.include_toolbar.toolbar
import javax.inject.Inject

class MoreFragment : MainFragment() {

    private val moreViewModel: MoreViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MoreViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val logoutConfirmationDialog: AlertDialog? by lazy {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.AppCompatAlertDialogStyle)

            builder.setMessage(R.string.prompt_log_out_confirmation)
                    .setTitle(getString(R.string.more_item_label_log_out))
                    .setPositiveButton(R.string.yes) { _, _ ->
                        moreViewModel.clickLogoutConfirmationDialogButton(true)
                    }
                    .setNegativeButton(R.string.no) { _, _ -> moreViewModel.clickLogoutConfirmationDialogButton(false) }
                    .setOnCancelListener { moreViewModel.clickLogoutConfirmationDialogButton(false) }

            builder.create()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_more, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setTitle(R.string.title_more)

        setUpRecyclerView()

        subscribeUI()
    }

    private fun setUpRecyclerView() {
        with (recyclerViewMore) {
            val itemsList = moreViewModel.itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList, object : OnItemClickListener {
                override fun onItemClick(index: Int, holder: MoreRecyclerViewAdapter.ViewHolder) {
                    moreViewModel.selectItem(index)
                }
            })

            setHasFixedSize(true)
        }
    }

    private fun goToAbout(iconView: View, label: String) {
        activity?.let {
            AboutActivity.start(it as AppCompatActivity, Pair(iconView, label))
        }
    }

    private fun subscribeUI() {
        moreViewModel.displayLogoutDialog.observe(this, Observer {
            logoutConfirmationDialog.takeIf { it != null && !it.isShowing }?.let { dialog ->
                if (it == true) {
                    dialog.show()
                } else {
                    dialog.dismiss()
                }
            }
        })

        moreViewModel.displayMessage.observe(this, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        moreViewModel.activityToGoTo.observe(this, EventObserver {
            if (it == AboutActivity::class.java) {
                val aboutItemView = recyclerViewMore.getChildAt(MoreViewModel.ItemsIndex.ABOUT.ordinal)
                with (recyclerViewMore.getChildViewHolder(aboutItemView) as MoreRecyclerViewAdapter.ViewHolder) {
                    goToAbout(this.iconImageView, this.labelTextView.text.toString())
                }
            } else {
                with(Intent(context, it)) {
                    startActivity(this)
                    activity?.finish()
                }
            }
        })

        moreViewModel.loading.observe(this, Observer {
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

    companion object {
        const val TAG = "MoreFragment"
        fun newInstance() = MoreFragment()
    }
}
