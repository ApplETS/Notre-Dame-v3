package ca.etsmtl.etsmobile.presentation.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
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

    val studentViewModel: StudentViewModel by lazy {
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

            subscribeUI()

            savedInstanceState?.let {
                toolbar.title = it.getCharSequence(TITLE_KEY)
                toolbar.subtitle = it.getCharSequence(SUBTITLE_KEY)
                if (it.getBoolean(SHOW_BACK_ICON_KEY)) {
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
                    toolbar.setNavigationOnClickListener { onBackPressed() }
                }
                tabsStudent.show(it.getBoolean(SHOW_TAB_LAYOUT))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with (outState) {
            val cours = studentViewModel.getCourse()
            putCharSequence(TITLE_KEY, cours?.sigle)
            putCharSequence(SUBTITLE_KEY, cours?.titreCours)
            putBoolean(SHOW_BACK_ICON_KEY, cours != null)
            putBoolean(SHOW_TAB_LAYOUT, cours != null)
        }

        super.onSaveInstanceState(outState)
    }

    /**
     * If the [GradesDetailsFragment] is shown, the event is consumed by hiding the
     * [GradesDetailsFragment] is hidden and the toolbar state is restored.
     *
     * @return True if the listener has consumed the event, false otherwise
     */
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
                    showGradesDetailsFragment(it)
                    appBarLayoutStudent.setExpanded(true)
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
                    toolbar.setNavigationOnClickListener { onBackPressed() }
                }
            } else {
                removeGradesDetailsFragment()
                toolbar.navigationIcon = null
            }

            tabsStudent.show(!it)
            toggleBottomNavigationView(!it)
        })

        studentViewModel.getTitle().observe(this, Observer {
            toolbar.title = it
        })

        studentViewModel.getSubtitle().observe(this, Observer { toolbar.subtitle = it })
    }

    companion object {
        private const val TAG = "StudentFragment"
        private const val TITLE_KEY = "StudentTitle"
        private const val SUBTITLE_KEY = "StudentSubtitle"
        private const val SHOW_BACK_ICON_KEY = "StudentBackIcon"
        private const val SHOW_TAB_LAYOUT = "StudentTabLayout"

        fun newInstance() = StudentFragment()
    }
}
