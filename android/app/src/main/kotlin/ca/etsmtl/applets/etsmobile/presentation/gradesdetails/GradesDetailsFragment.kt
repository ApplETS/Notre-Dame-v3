package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.repository.data.model.Cours
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.empty_view_courses_grades.btnRetry
import kotlinx.android.synthetic.main.empty_view_courses_grades.emptyViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades_details.recyclerGradesDetails
import kotlinx.android.synthetic.main.fragment_grades_details.swipeRefreshLayoutGradesDetails
import javax.inject.Inject

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsFragment : DaggerFragment() {

    private val gradesDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GradesDetailsViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val groupAdapter by lazy { GroupAdapter<ViewHolder>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_grades_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefresh()

        setUpRecyclerView()

        btnRetry.setOnClickListener { gradesDetailsViewModel.refresh() }

        subscribeUI()

        arguments?.getParcelable<Cours>(COURS_KEY)?.let { course ->
            gradesDetailsViewModel.cours.value = course
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(COURS_KEY, gradesDetailsViewModel.cours.value)

        super.onSaveInstanceState(outState)
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutGradesDetails.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutGradesDetails.setOnRefreshListener { gradesDetailsViewModel.refresh() }
    }

    private fun setUpRecyclerView() {
        recyclerGradesDetails.adapter = groupAdapter
        recyclerGradesDetails.layoutManager = LinearLayoutManager(context)
    }

    private fun subscribeUI() {
        gradesDetailsViewModel.loading.observe(this, Observer {
            swipeRefreshLayoutGradesDetails.isRefreshing = it == true
        })

        gradesDetailsViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        })

        gradesDetailsViewModel.recyclerViewItems.observe(this, Observer {
            it?.let { groupAdapter.update(it) }
        })

        gradesDetailsViewModel.showEmptyView.observe(this, Observer {
            emptyViewCoursesGrades.isVisible = it == true
            recyclerGradesDetails.isVisible = it == false
        })

        lifecycle.addObserver(gradesDetailsViewModel)
    }

    companion object {
        const val TAG = "GradesDetailsFragment"
        const val COURS_KEY = "CoursKey"

        fun newInstance(cours: Cours) = GradesDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable(COURS_KEY, cours) }
        }
    }
}