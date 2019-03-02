package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesAdapter
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesViewModel
import ca.etsmtl.applets.etsmobile.presentation.gradesdetails.GradesDetailsActivity
import ca.etsmtl.applets.etsmobile.util.EventObserver
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator
import kotlinx.android.synthetic.main.empty_view_courses_grades.emptyViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades.recyclerViewCoursesGrades
import kotlinx.android.synthetic.main.fragment_grades_card.progressBarGrades
import kotlinx.android.synthetic.main.item_grade_course.tvCourseSigle
import model.Cours
import javax.inject.Inject

/**
 * Created by Sonphil on 04-02-19.
 */

class GradesCardFragment : DaggerFragment() {

    private val gradesViewModel: GradesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GradesViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter: GradesAdapter by lazy {
        GradesAdapter(object : GradesAdapter.OnCourseClickListener {
            override fun onCourseClick(cours: Cours, holder: GradesAdapter.CourseGradeViewHolder) {
                this@GradesCardFragment.activity?.let {
                    GradesDetailsActivity.start(
                        it as AppCompatActivity,
                        holder.itemView,
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
    ): View = inflater.inflate(R.layout.fragment_grades_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        subscribeUI()
    }
    private fun setUpRecyclerView() {
        recyclerViewCoursesGrades.adapter = adapter
        recyclerViewCoursesGrades.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW).apply {
            justifyContent = JustifyContent.FLEX_START
        }
        recyclerViewCoursesGrades.itemAnimator = FadeInDownAnimator()
    }

    private fun subscribeUI() {
        gradesViewModel.cours.observe(this, Observer {
            it?.takeIf { it.isNotEmpty() }?.let { adapter.items = it }
        })

        gradesViewModel.showEmptyView.observe(this, Observer {
            recyclerViewCoursesGrades.isVisible = it == false
            emptyViewCoursesGrades.isVisible = it == true
        })

        gradesViewModel.loading.observe(this, Observer {
            progressBarGrades.isVisible = it
        })

        gradesViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        })

        this.lifecycle.addObserver(gradesViewModel)
    }

    companion object {
        fun newInstance() = GradesCardFragment()
    }
}