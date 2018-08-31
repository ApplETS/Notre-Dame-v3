package ca.etsmtl.etsmobile.presentation.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.MainFragment
import ca.etsmtl.etsmobile.presentation.grades.GradesDetailsFragment
import ca.etsmtl.etsmobile.util.show
import ca.etsmtl.repository.data.model.Cours
import kotlinx.android.synthetic.main.fragment_student.appBarLayoutStudent
import kotlinx.android.synthetic.main.fragment_student.tabsStudent
import kotlinx.android.synthetic.main.fragment_student.viewPagerStudent
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * This fragment contains a [TabLayout] and a [ViewPager] that let the user switch between
 * [GradesFragment] and [ProfileFragment].
 *
 * The fragment can also display [GradesDetailsFragment] by calling [displayGradesDetailsFragment].
 * By doing so, [GradesDetailsFragment] would be displayed instead of the [ViewPager]
 *
 * Created by Sonphil on 24-02-18.
 */

class StudentFragment : MainFragment() {

    /** The current course displayed in [GradesDetailsFragment] **/
    private var currentCourse: Cours? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            savedInstanceState?.getParcelable<Cours>(CURRENT_COURSE_GRADES_DETAILS_KEY)?.let {
                currentCourse = it
                setUpToolBarForDisplayingCourseGradesDetails(it)
            }

            if (currentCourse == null) {
                setUpToolbar()
            }

            viewPagerStudent.adapter = StudentPagerAdapter(it, childFragmentManager)
            tabsStudent.setupWithViewPager(viewPagerStudent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_COURSE_GRADES_DETAILS_KEY, currentCourse)

        super.onSaveInstanceState(outState)
    }

    /**
     * Display grades details for a given course
     *
     * @param cours
     */
    fun displayGradesDetailsFragment(cours: Cours) {
        with(childFragmentManager.beginTransaction()) {
            replace(R.id.content, GradesDetailsFragment.newInstance(cours), GradesDetailsFragment.TAG)

            commit()
        }

        currentCourse = cours
        setUpToolBarForDisplayingCourseGradesDetails(cours)
    }

    private fun setUpToolbar() {
        toolbar.setTitle(R.string.title_student)
        toolbar.subtitle = null
        tabsStudent.show(true)
        toolbar.navigationIcon = null
    }

    private fun setUpToolBarForDisplayingCourseGradesDetails(cours: Cours) {
        toolbar.title = cours.sigle
        toolbar.subtitle = cours.titreCours
        tabsStudent.show(false)
        appBarLayoutStudent.setExpanded(true)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * If the [GradesDetailsFragment] is shown, the event is consumed by hiding the
     * [GradesDetailsFragment] is hidden and the toolbar state is restored.
     */
    override fun onBackPressed(): Boolean {
        return if (tabsStudent.visibility != View.VISIBLE) {
            with(childFragmentManager.beginTransaction()) {
                childFragmentManager.findFragmentByTag(GradesDetailsFragment.TAG)?.let {
                    remove(it)
                    currentCourse = null
                }
                commit()
            }

            setUpToolbar()

            true
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "StudentFragment"
        private const val CURRENT_COURSE_GRADES_DETAILS_KEY = "CurrentCourseGradesDetails"

        fun newInstance() = StudentFragment()
    }
}
