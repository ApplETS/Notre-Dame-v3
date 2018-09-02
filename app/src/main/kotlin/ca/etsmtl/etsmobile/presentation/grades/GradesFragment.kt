package ca.etsmtl.etsmobile.presentation.grades

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.student.StudentViewModel
import ca.etsmtl.etsmobile.util.EventObserver
import ca.etsmtl.etsmobile.util.show
import ca.etsmtl.repository.data.model.Cours
import com.xiaofeng.flowlayoutmanager.Alignment
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.android.synthetic.main.empty_view_courses_grades.emptyViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades.recyclerViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades.swipeRefreshLayoutCoursesGrades
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
    private val studentViewModel: StudentViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it, viewModelFactory).get(StudentViewModel::class.java) }
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter: GradesAdapter by lazy {
        GradesAdapter(object : GradesAdapter.OnCourseClickListener {
            override fun onCourseClick(cours: Cours, holder: GradesAdapter.CourseGradeViewHolder) {
                currentCourseShown = cours
                studentViewModel?.setCourse(cours)
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
        subscribeUI()

        savedInstanceState?.getParcelable<Cours>(CURRENT_COURSE_SHOWN_KEY)?.let { course ->
            studentViewModel?.setCourse(course)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_COURSE_SHOWN_KEY, currentCourseShown)

        super.onSaveInstanceState(outState)
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutCoursesGrades.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutCoursesGrades.setOnRefreshListener { gradesViewModel.refresh() }
    }

    private fun setUpRecyclerView() {
        recyclerViewCoursesGrades.adapter = adapter
        // Set items margins as an ItemDecoration because margins don't works with FlowLayoutManager
        // library
        recyclerViewCoursesGrades.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                with (resources) {
                    outRect.apply {
                        bottom = 0
                        right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
                                this@with.displayMetrics).toInt()
                        left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
                                this@with.displayMetrics).toInt()
                        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f,
                                this@with.displayMetrics).toInt()
                    }
                }
            }
        })
        recyclerViewCoursesGrades.layoutManager = FlowLayoutManager().apply {
            this.isAutoMeasureEnabled = true
            setAlignment(Alignment.LEFT)
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
        private const val CURRENT_COURSE_SHOWN_KEY = "CurrentCourseShown"

        fun newInstance() = GradesFragment()
    }
}
