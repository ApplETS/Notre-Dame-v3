package ca.etsmtl.applets.etsmobile.presentation.schedule

import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ca.etsmtl.applets.repository.data.model.Seance
import java.util.Calendar
import java.util.Date

/**
 * Created by mykaelll87 on 2019-01-10
 */
class ScheduleAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var items: Map<Pair<Calendar, Calendar>, List<Seance>> = emptyMap()

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