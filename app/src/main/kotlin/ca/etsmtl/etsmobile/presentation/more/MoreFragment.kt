package ca.etsmtl.etsmobile.presentation.more

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.MainActivity
import ca.etsmtl.etsmobile.presentation.MainActivityBackKeyListener
import ca.etsmtl.etsmobile.presentation.WelcomeActivity
import ca.etsmtl.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.etsmobile.presentation.more.MoreRecyclerViewAdapter.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_more.progressMore
import kotlinx.android.synthetic.main.fragment_more.recyclerViewMore
import kotlinx.android.synthetic.main.include_toolbar.toolbar
import javax.inject.Inject

class MoreFragment : DaggerFragment(), MainActivityBackKeyListener {

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

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val mainActivity = activity as? MainActivity

        mainActivity?.setBackKeyListener(this)
    }

    override fun onDetach() {
        super.onDetach()

        val mainActivity = activity as? MainActivity

        mainActivity?.setBackKeyListener(null)
    }

    private fun setUpRecyclerView(view: RecyclerView) {
        with(view) {
            val itemsList = moreViewModel.itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList, object : OnItemClickListener {
                override fun onItemClick(index: Int) {
                    when (index) {
                        MoreViewModel.ItemsIndex.FAQ.ordinal -> Log.d(TAG, "FAQ") // TODO
                        MoreViewModel.ItemsIndex.ABOUT.ordinal -> goToAbout()
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
    private fun goToAbout() {
        val intent = Intent(context, AboutActivity::class.java)
        startActivity(intent)
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
