package ca.etsmtl.applets.etsmobile.presentation.student

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesFragment
import ca.etsmtl.applets.etsmobile.presentation.profile.ProfileFragment

class StudentPagerAdapter(
    context: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val GRADES = 0
        const val PROFILE = 1
    }

    private val context = context.applicationContext

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            GRADES -> GradesFragment.newInstance()
            else -> ProfileFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            GRADES -> context.getString(R.string.title_grades)
            PROFILE -> context.getString(R.string.title_profile)
            else -> null
        }
    }
}