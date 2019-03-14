package ca.etsmtl.applets.etsmobile.presentation.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.fadeTo
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.fragment_student.viewPagerStudent

/**
 * This fragment contains a [TabLayout] and a [ViewPager] that let the user switch between
 * [GradesFragment] and [ProfileFragment].
 *
 * Created by Sonphil on 24-02-18.
 */

class StudentFragment : DaggerFragment() {

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

        (activity as? MainActivity)?.let { activity ->
            activity.tabLayout?.let { tabLayout ->
                viewPagerStudent.adapter = StudentPagerAdapter(activity, childFragmentManager)
                tabLayout.setupWithViewPager(viewPagerStudent)
                tabLayout.fadeTo(View.VISIBLE)
            }
        }
    }

    override fun onDestroyView() {
        (activity as? MainActivity)?.let {
            it.appBarLayout.setExpanded(true, false)
            it.tabLayout.setupWithViewPager(null)
            it.tabLayout.isVisible = false
        }

        super.onDestroyView()
    }

    companion object {
        private const val TAG = "StudentFragment"

        fun newInstance() = StudentFragment()
    }
}
