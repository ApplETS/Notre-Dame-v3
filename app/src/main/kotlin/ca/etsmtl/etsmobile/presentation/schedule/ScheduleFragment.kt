package ca.etsmtl.etsmobile.presentation.schedule


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.etsmtl.etsmobile.R


/**
 * Created by Sonphil on 25-02-18.
 */

class ScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    companion object {
        fun newInstance(): ScheduleFragment {
            val fragment = ScheduleFragment()
            return fragment
        }
    }

}// Required empty public constructor
