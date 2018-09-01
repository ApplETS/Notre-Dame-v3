package ca.etsmtl.etsmobile.presentation.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.MainFragment
import ca.etsmtl.etsmobile.presentation.grades.GradesDetailsFragment
import ca.etsmtl.etsmobile.presentation.grades.StudentViewModel
import ca.etsmtl.etsmobile.util.EventObserver
import ca.etsmtl.etsmobile.util.show
import ca.etsmtl.repository.data.model.Cours
import kotlinx.android.synthetic.main.fragment_student.appBarLayoutStudent
import kotlinx.android.synthetic.main.fragment_student.tabsStudent
import kotlinx.android.synthetic.main.fragment_student.viewPagerStudent
import kotlinx.android.synthetic.main.include_toolbar.toolbar
import javax.inject.Inject

/**
 * This fragment contains a [TabLayout] and a [ViewPager] that let the user switch between
 * [GradesFragment] and [ProfileFragment].
 *
 * The fragment can also display [GradesDetailsFragment] by calling [displayGradesDetailsFragment].
 * If so, [GradesDetailsFragment] would be displayed instead of the [ViewPager]
 *
 * Created by Sonphil on 24-02-18.
 */

class StudentFragment : MainFragment() {

    private val studentViewModel: StudentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(StudentViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
            viewPagerStudent.adapter = StudentPagerAdapter(it, childFragmentManager)
            tabsStudent.setupWithViewPager(viewPagerStudent)
            tabsStudent.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab) {}

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabSelected(tab: TabLayout.Tab) {
                    studentViewModel.selectTab(tab.position)
                }
            })

            subscribeUI()
        }
    }

    override fun onBackPressed() = studentViewModel.back()

    private fun subscribeUI() {
        studentViewModel.displayCourseGradesDetails.observe(this, EventObserver {
            fun removeGradesDetailsFragment() {
                with(childFragmentManager.beginTransaction()) {
                    childFragmentManager.findFragmentByTag(GradesDetailsFragment.TAG)?.let {
                        remove(it)
                    }
                    commit()
                }
            }

            fun showGradesDetailsFragment(cours: Cours) {
                with(childFragmentManager.beginTransaction()) {
                    replace(R.id.content, GradesDetailsFragment.newInstance(cours), GradesDetailsFragment.TAG)

                    commit()
                }
            }

            if (it) {
                studentViewModel.getCourse()?.let {
                    appBarLayoutStudent.setExpanded(true)
                    showGradesDetailsFragment(it)
                }
            } else {
                removeGradesDetailsFragment()
            }
        })

        studentViewModel.title.observe(this, Observer { toolbar.title = it })

        studentViewModel.subtitle.observe(this, Observer { toolbar.subtitle = it })

        studentViewModel.showBackIcon.observe(this, Observer {
            if (it == true) {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
                toolbar.setNavigationOnClickListener { onBackPressed() }
            } else {
                toolbar.navigationIcon = null
            }
        })

        studentViewModel.showTabs.observe(this, Observer {
            tabsStudent.show(it == true)
        })

        studentViewModel.showBottomNavigationView.observe(this, Observer {
            toggleBottomNavigationView(it == true)
        })
    }

    companion object {
        private const val TAG = "StudentFragment"

        fun newInstance() = StudentFragment()
    }
}
