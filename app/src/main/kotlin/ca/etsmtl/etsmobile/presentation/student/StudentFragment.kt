package ca.etsmtl.etsmobile.presentation.student

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import kotlinx.android.synthetic.main.fragment_student.tabsStudent
import kotlinx.android.synthetic.main.fragment_student.viewPagerStudent
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * This fragment contains a [TabLayout] and a [ViewPager] that let the user switch between
 * [GradesFragment] and [ProfileFragment].
 *
 * Created by Sonphil on 24-02-18.
 */

class StudentFragment : Fragment() {

    companion object {
        private const val STUDENT_FRAGMENT_TAG = "StudentFragment"
        fun newInstance() = StudentFragment()
    }

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
            toolbar.setTitle(R.string.title_student)
            viewPagerStudent.adapter = StudentPagerAdapter(it, childFragmentManager)
            tabsStudent.setupWithViewPager(viewPagerStudent)
        }
    }
}
