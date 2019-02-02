package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.etsmtl.applets.etsmobile.R

/**
 * Created by Sonphil on 24-01-19.
 */

class TodayScheduleCardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_today_schedule_card, container, false)

    companion object {
        val TAG = "TodayScheduleCardFragment"
        fun newInstance() = TodayScheduleCardFragment()
    }
}