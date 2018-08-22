package ca.etsmtl.etsmobile.presentation.grades

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.util.EventObserver
import ca.etsmtl.repository.data.model.Cours
import com.moos.library.CircleProgressView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_grades_details.progressViewAverage
import kotlinx.android.synthetic.main.fragment_grades_details.progressViewGrade
import kotlinx.android.synthetic.main.fragment_grades_details.swipeRefreshLayoutGradesDetails
import javax.inject.Inject

/**
 * Created by Sonphil on 15-08-18.
 */

class GradesDetailsFragment : DaggerFragment() {

    private val gradesDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GradesDetailsViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grades_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefresh()

        subscribeUI()

        arguments?.let { gradesDetailsViewModel.cours.value = it.getParcelable(COURS_KEY) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(COURS_KEY, gradesDetailsViewModel.cours.value)

        super.onSaveInstanceState(outState)
    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayoutGradesDetails.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayoutGradesDetails.setOnRefreshListener { gradesDetailsViewModel.refresh() }
    }

    private fun subscribeUI() {
        gradesDetailsViewModel.getLoading().observe(this, Observer {
            swipeRefreshLayoutGradesDetails.isRefreshing = it == true
        })

        gradesDetailsViewModel.errorMessage.observe(this, EventObserver {
            it?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        })

        gradesDetailsViewModel.getGradePercentage().observe(this, Observer {
            setCircleProgressViewProgress(progressViewGrade, it)
        })

        gradesDetailsViewModel.getAveragePercentage().observe(this, Observer {
            setCircleProgressViewProgress(progressViewAverage, it)
        })

        lifecycle.addObserver(gradesDetailsViewModel)
    }

    private fun setCircleProgressViewProgress(circleProgressView: CircleProgressView, progress: Float?) {
        progress?.let {
            with (circleProgressView) {
                setEndProgress(it)
                startProgressAnimation()
            }
        }
    }

    companion object {
        const val TAG = "GradesDetailsFragment"
        const val COURS_KEY = "CoursKey"

        fun newInstance(cours: Cours) = GradesDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable(COURS_KEY, cours) }
        }
    }
}