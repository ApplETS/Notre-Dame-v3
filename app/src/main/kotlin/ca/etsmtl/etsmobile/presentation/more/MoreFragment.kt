package ca.etsmtl.etsmobile.presentation.more

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.MainFragment
import ca.etsmtl.etsmobile.presentation.WelcomeActivity
import ca.etsmtl.etsmobile.presentation.about.AboutActivity
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
    }

    private fun setUpRecyclerView(view: RecyclerView) {
        with(view) {
            val itemsList = moreViewModel.itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList, object : OnItemClickListener {
                override fun onItemClick(index: Int, viewHolder: MoreRecyclerViewAdapter.ViewHolder) {
                    when (index) {
                        MoreViewModel.ItemsIndex.FAQ.ordinal -> Log.d(TAG, "FAQ") // TODO
                        MoreViewModel.ItemsIndex.ABOUT.ordinal -> goToAbout(viewHolder.iconImageView, viewHolder.labelTextView.text.toString())
                        MoreViewModel.ItemsIndex.LOGOUT.ordinal -> displayLogoutConfirmationDialog(context)
                    }
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
                    recyclerViewMore.visibility = View.GONE
                    progressMore.visibility = View.VISIBLE
                    subscribeUILogout()
                }
                .setNegativeButton(R.string.no) { dialog, which -> dialog.dismiss() }

        builder.create().show()
    }

    private fun subscribeUILogout() {
        moreViewModel.logout().observe(this, Observer<Boolean> { finished ->
            if (finished != null && finished) {
                Toast.makeText(context, R.string.msg_logout_success,
                        Toast.LENGTH_LONG).show()

                val intent = Intent(context, WelcomeActivity::class.java)
                startActivity(intent)

                // Finish the activity to prevent the user from going back
                activity?.finish()
            }
        })
    }

    /**
     * Starts AboutActivity
     */
    private fun goToAbout(iconView: View, label: String) {
        activity?.let {
            AboutActivity.start(it as AppCompatActivity, Pair(iconView, label))
        }
    }

    private fun goToFragment(fragment: Fragment, fragmentTag: String, title: String) {
        toolbar.title = title

        with(childFragmentManager.beginTransaction()) {
            replace(R.id.containerItem, fragment, fragmentTag)
            addToBackStack(fragmentTag)
            commit()
        }
    }

    override fun onBackPressed(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 0) {
            toolbar.setTitle(R.string.title_more)
            childFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }
}
