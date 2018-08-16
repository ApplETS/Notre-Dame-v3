package ca.etsmtl.etsmobile.presentation.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.Cours
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_grades_details.tvCourse

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsFragment : DaggerFragment() {
    var cours: Cours? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        savedInstanceState?.let {
            cours = it.getParcelable(COURS_KEY)
        }

        return inflater.inflate(R.layout.fragment_grades_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCourse.text = cours.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(COURS_KEY, cours)

        super.onSaveInstanceState(outState)
    }

    companion object {
        const val TAG = "GradesDetailsFragment"
        const val COURS_KEY = "CoursKey"

        fun newInstance(cours: Cours) = GradesDetailsFragment().apply {
            this.cours = cours
        }
    }
}