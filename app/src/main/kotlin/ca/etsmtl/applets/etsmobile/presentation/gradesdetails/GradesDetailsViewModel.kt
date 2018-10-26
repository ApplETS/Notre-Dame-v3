package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchGradesDetailsUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Evaluation
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.data.model.SommaireEtEvaluations
import ca.etsmtl.applets.repository.util.zeroIfNullOrBlank
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import javax.inject.Inject

/**
 * Created by Sonphil on 22-08-18.
 */

class GradesDetailsViewModel @Inject constructor(
    private val fetchGradesDetailsUseCase: FetchGradesDetailsUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    val cours = MutableLiveData<Cours>()
    private var summaryAndEvaluationsRes: LiveData<Resource<SommaireEtEvaluations>>? = null
    private val summaryAndEvaluationsMediatorLiveData: MediatorLiveData<Resource<SommaireEtEvaluations>> by lazy {
        MediatorLiveData<Resource<SommaireEtEvaluations>>()
    }
    val errorMessage: LiveData<Event<String?>> by lazy {
        Transformations.map(summaryAndEvaluationsMediatorLiveData) {
            if (it.status == Resource.Status.ERROR) {
                when {
                    !app.isDeviceConnected() -> {
                        Event(app.getString(R.string.error_no_internet_connection))
                    }
                    else -> Event(app.getString(R.string.error_failed_to_retrieve_data_course))
                }
            } else {
                Event(it.message)
            }
        }
    }
    val recyclerViewItems: LiveData<List<Group>> = Transformations.map(summaryAndEvaluationsMediatorLiveData) {
        fun getSummaryItems(sommaireElementsEvaluation: SommaireElementsEvaluation) = listOf(
                EvaluationDetailItem(app.getString(R.string.label_median), sommaireElementsEvaluation.medianeClasse),
                EvaluationDetailItem(app.getString(R.string.label_standard_deviation), sommaireElementsEvaluation.ecartTypeClasse),
                EvaluationDetailItem(app.getString(R.string.label_percentile_rank), sommaireElementsEvaluation.rangCentileClasse)
        )

        fun getEvaluationDetailItems(grade: String, average: String, evaluation: Evaluation) = listOf(
                EvaluationDetailItem(
                        app.getString(R.string.label_grade),
                        grade
                ),
                EvaluationDetailItem(
                        app.getString(R.string.label_average),
                        average
                ),
                EvaluationDetailItem(
                        app.getString(R.string.label_median),
                        evaluation.mediane
                ),
                EvaluationDetailItem(
                        app.getString(R.string.label_standard_deviation),
                        evaluation.ecartType
                ),
                EvaluationDetailItem(
                        app.getString(R.string.label_percentile_rank),
                        evaluation.rangCentile
                ),
                EvaluationDetailItem(
                        app.getString(R.string.label_target_date),
                        evaluation.dateCible
                )
        )

        it?.takeIf { it.status != Resource.Status.LOADING }?.data?.let {
            val gradeAverageItem = it.sommaireElementsEvaluation.run {
                GradeAverageItem(
                        cours.value?.cote,
                        note.zeroIfNullOrBlank(),
                        noteSur.zeroIfNullOrBlank(),
                        noteSur100.zeroIfNullOrBlank(),
                        moyenneClasse.zeroIfNullOrBlank(),
                        moyenneClassePourcentage.zeroIfNullOrBlank()
                )
            }

            mutableListOf<Group>(gradeAverageItem).apply {
                add(SectionTitleItem(app.getString(R.string.title_section_summary)))

                addAll(getSummaryItems(it.sommaireElementsEvaluation))

                add(SectionTitleItem(app.getString(R.string.title_section_evaluations)))

                val evaluationsItems = it.evaluations.map {
                    ExpandableGroup(EvaluationHeaderItem(it)).apply {
                        val grade = String.format(
                                app.getString(R.string.text_grade_with_percentage),
                                it.note.zeroIfNullOrBlank(),
                                it.corrigeSur.zeroIfNullOrBlank(),
                                it.notePourcentage.zeroIfNullOrBlank()
                        )

                        val averageStr = String.format(
                                app.getString(R.string.text_grade_with_percentage),
                                it.moyenne.zeroIfNullOrBlank(),
                                it.corrigeSur.zeroIfNullOrBlank(),
                                it.moyennePourcentage.zeroIfNullOrBlank()
                        )

                        add(Section(getEvaluationDetailItems(grade, averageStr, it)))
                    }
                }

                addAll(evaluationsItems)
            }
        }
    }
    val showEmptyView: LiveData<Boolean> = Transformations.map(summaryAndEvaluationsMediatorLiveData) {
        (it.status != Resource.Status.LOADING && (it?.data?.evaluations == null || it.data?.evaluations?.isEmpty() == true))
    }

    fun getLoading(): LiveData<Boolean> = Transformations.map(summaryAndEvaluationsMediatorLiveData) {
        it.status == Resource.Status.LOADING
    }

    private fun load() {
        cours.value?.let {
            summaryAndEvaluationsRes = fetchGradesDetailsUseCase(it).apply {
                summaryAndEvaluationsMediatorLiveData.addSource(this) {
                    summaryAndEvaluationsMediatorLiveData.value = it
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun refresh() {
        summaryAndEvaluationsRes?.let {
            summaryAndEvaluationsMediatorLiveData.removeSource(it)
            summaryAndEvaluationsRes = null
        }

        load()
    }
}