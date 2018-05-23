package ca.etsmtl.etsmobile.presentation.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.etsmtl.etsmobile.R

/**
 * The fragment displayed to the user after he has successfully log in
 *
 * Created by Sonphil on 24-02-18.
 */

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {

        fun newInstance() = HomeFragment()
    }
}
