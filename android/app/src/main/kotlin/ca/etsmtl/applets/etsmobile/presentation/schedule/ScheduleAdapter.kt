package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.os.Bundle
import android.util.Range
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ca.etsmtl.applets.etsmobile.presentation.schedule.week.ScheduleWeekFragment
import ca.etsmtl.applets.repository.data.model.Seance
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by mykaelll87 on 2019-01-10
 */
class ScheduleAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var itemList: List<Map.Entry<Range<Calendar>, List<Seance>>> = emptyList()
    var items: Map<Range<Calendar>, List<Seance>> = emptyMap()
        set(value) {
            field=value
            val newItemsList = mutableListOf<Map.Entry<Range<Calendar>, List<Seance>>>().apply {
                value.forEach { this.add(it) }
            }

            itemList = newItemsList

        }

    fun getCurrentPosition(): Int{
        val cal =  Calendar.getInstance()
        var i = 0

        while (i < itemList.size){
            if ((cal in itemList[i].key) or (cal < itemList[i].key.lower)) return i

            ++i
        }

        return i-1
    }


    override fun getItem(position: Int): Fragment = ScheduleWeekFragment().apply {
        arguments = Bundle().apply {
            putSerializable(ScheduleWeekFragment.LIST_TAG, ArrayList<Seance>()
                .apply { itemList[position].value.forEach { this.add(it) } })
        }
    }

    override fun getCount(): Int = itemList.size

    override fun getPageTitle(position: Int): CharSequence? {
        val range = itemList[position].key

        return SimpleDateFormat
            .getDateInstance(DateFormat.DEFAULT, Locale.CANADA_FRENCH)
            .format(range.lower.time)
    }

    //TODO: see https://developer.android.com/training/implementing-navigation/lateral
}