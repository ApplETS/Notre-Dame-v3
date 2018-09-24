package ca.etsmtl.etsmobile.presentation.student

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.main.MainFragment
import kotlinx.android.synthetic.main.fragment_student.tabsStudent
import kotlinx.android.synthetic.main.fragment_student.viewPagerStudent
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * This fragment contains a [TabLayout] and a [ViewPager] that let the user switch between
 * [GradesFragment] and [ProfileFragment].
 *
 * Created by Sonphil on 24-02-18.
 */

class StudentFragment : MainFragment() {

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
        }

        toolbar.setTitle(R.string.title_student)
    }

    companion object {
        private const val TAG = "StudentFragment"

        fun newInstance() = StudentFragment()
    }
}
