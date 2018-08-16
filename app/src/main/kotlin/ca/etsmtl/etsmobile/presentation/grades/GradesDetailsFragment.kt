package ca.etsmtl.etsmobile.presentation.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.repository.data.model.Cours
import dagger.android.support.DaggerFragment

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsFragment : DaggerFragment() {
    companion object {
        const val TAG = "GradesDetailsFragment"

        fun newInstance(course: Cours) = GradesDetailsFragment().apply {
            setCours(course)
        }
    }

    fun setCours(cours: Cours) { }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}