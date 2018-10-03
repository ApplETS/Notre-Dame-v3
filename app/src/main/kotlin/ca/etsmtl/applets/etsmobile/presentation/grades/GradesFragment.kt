package ca.etsmtl.applets.etsmobile.presentation.grades

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.EventObserver
import ca.etsmtl.applets.etsmobile.util.show
import ca.etsmtl.applets.repository.data.model.Cours
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.android.synthetic.main.empty_view_courses_grades.btnRetry
import kotlinx.android.synthetic.main.empty_view_courses_grades.emptyViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades.recyclerViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades.swipeRefreshLayoutCoursesGrades
import kotlinx.android.synthetic.main.item_grade_course.tvCourseGrade
import kotlinx.android.synthetic.main.item_grade_course.tvCourseSigle
import javax.inject.Inject

/**
 * This fragment shows the grades of the user.
 *
 * Created by Sonphil on 24-02-18.
 */

class GradesFragment : DaggerFragment() {

    private var currentCourseShown: Cours? = null
    private val gradesViewModel: GradesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GradesViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter: GradesAdapter by lazy {
        GradesAdapter(object : GradesAdapter.OnCourseClickListener {
            override fun onCourseClick(cours: Cours, holder: GradesAdapter.CourseGradeViewHolder) {
                currentCourseShown = cours
                this@GradesFragment.activity?.let {
                    GradesDetailsActivity.start(
                            it as AppCompatActivity,
                            holder.tvCourseGrade,
                            holder.tvCourseSigle,
                            cours
                    )
                }
            }
        })
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
        btnRetry.setOnClickListener { gradesViewModel.refresh() }
        subscribeUI()
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutCoursesGrades.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutCoursesGrades.setOnRefreshListener { gradesViewModel.refresh() }
    }

    private fun setUpRecyclerView() {
        recyclerViewCoursesGrades.adapter = adapter
        recyclerViewCoursesGrades.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW).apply {
            justifyContent = JustifyContent.FLEX_START
        }
        recyclerViewCoursesGrades.itemAnimator = FadeInUpAnimator()
    }

    private fun subscribeUI() {
        gradesViewModel.getCours().observe(this, Observer {
            it?.takeIf { it.isNotEmpty() }?.let { adapter.items = it }
        })

        gradesViewModel.getShowEmptyView().observe(this, Observer {
            recyclerViewCoursesGrades.show(it == false)
            emptyViewCoursesGrades.show(it == true)
        })

        gradesViewModel.getLoading().observe(this, Observer {
            it?.let { swipeRefreshLayoutCoursesGrades.isRefreshing = it }
        })

        gradesViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        this.lifecycle.addObserver(gradesViewModel)
    }

    companion object {
        private const val TAG = "GradesFragment"

        fun newInstance() = GradesFragment()
    }
}
