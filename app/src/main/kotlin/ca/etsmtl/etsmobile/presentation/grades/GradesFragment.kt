package ca.etsmtl.etsmobile.presentation.grades

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_grades.recyclerViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades.swipeRefreshLayoutCoursesGrades
import javax.inject.Inject

/**
 * This fragment shows the grades of the user.
 *
 * Created by Sonphil on 24-02-18.
 */

class GradesFragment : DaggerFragment() {

    private val gradesViewModel: GradesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GradesViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter: GradesAdapter by lazy {
        GradesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grades, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefresh()
        setUpRecyclerView()
        subscribeUI()
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutCoursesGrades.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutCoursesGrades.setOnRefreshListener { gradesViewModel.refresh() }
    }

    private fun setUpRecyclerView() {
        // TODO: Fill?
        recyclerViewCoursesGrades.layoutManager = GridLayoutManager(context, 3)
        recyclerViewCoursesGrades.adapter = adapter
    }

    private fun subscribeUI() {
        gradesViewModel.getCours().observe(this, Observer {
            it?.let { adapter.setCourses(it) }
        })
        gradesViewModel.getLoading().observe(this, Observer {
            it?.let { swipeRefreshLayoutCoursesGrades.isRefreshing = it }
        })
        gradesViewModel.getErrorMessage().observe(this, Observer {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })
        this.lifecycle.addObserver(gradesViewModel)
    }

    companion object {
        private const val GRADES_FRAGMENT_TAG = "GradesFragment"
        fun newInstance() = GradesFragment()
    }
}
