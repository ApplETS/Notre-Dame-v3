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
import ca.etsmtl.etsmobile.presentation.WelcomeActivity
import ca.etsmtl.etsmobile.presentation.more.MoreRecyclerViewAdapter.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_more.progress_more
import kotlinx.android.synthetic.main.fragment_more.recycler_view_more
import javax.inject.Inject

class MoreFragment : DaggerFragment() {

    companion object {
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

        setUpRecyclerView(recycler_view_more)
    }

    private fun setUpRecyclerView(view: RecyclerView) {
        with(view) {
            val itemsList = moreViewModel.itemsList()

            adapter = MoreRecyclerViewAdapter(itemsList, object : OnItemClickListener {
                override fun onItemClick(index: Int) {
                    displayLogoutConfirmationDialog(context)
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
                    recycler_view_more.visibility = View.GONE
                    progress_more.visibility = View.VISIBLE
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
}
