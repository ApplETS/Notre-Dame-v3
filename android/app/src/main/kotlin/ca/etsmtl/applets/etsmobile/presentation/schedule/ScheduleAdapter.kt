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

//            val diffCallback = object: DiffUtil.Callback(){
//                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//                    itemList[oldItemPosition].key == newItemsList[newItemPosition].value
//
//
//                override fun getOldListSize(): Int = itemList.size
//
//                override fun getNewListSize(): Int = newItemsList.size
//
//                override fun areContentsTheSame(
//                    oldItemPosition: Int,
//                    newItemPosition: Int
//                ): Boolean = itemList[oldItemPosition].value == newItemsList[oldItemPosition].value
//            }

//            val diffResult = DiffUtil.calculateDiff(diffCallback)
            itemList = newItemsList
//            diffResult.dispatchUpdatesTo(this)
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