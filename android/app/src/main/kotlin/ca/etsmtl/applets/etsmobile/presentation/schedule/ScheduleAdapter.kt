package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.graphics.Typeface
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.schedule.Week.ScheduleWeekInnerListAdapter
import ca.etsmtl.applets.repository.data.model.Seance
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*
import java.util.Date

/**
 * Created by mykaelll87 on 2019-01-10
 */
class ScheduleAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        TODO("not implemented")
    }

    override fun getCount(): Int {
        TODO("not implemented")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }

    //TODO: see https://developer.android.com/training/implementing-navigation/lateral
    private var itemList: List<Map.Entry<Date, List<Seance>>> = emptyList()

}