package ca.etsmtl.etsmobile.presentation.student

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.grades.GradesFragment
import ca.etsmtl.etsmobile.presentation.profile.ProfileFragment

class StudentPagerAdapter : FragmentPagerAdapter {

    companion object {
        const val GRADES = 0
        const val PROFILE = 1
    }

    private var context: Context

    constructor(context: Context, fragmentManager: FragmentManager) : super(fragmentManager) {
        this.context = context.applicationContext
    }

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            GRADES -> GradesFragment.newInstance()
            PROFILE -> ProfileFragment.newInstance()
            else -> null
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