package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
import ca.etsmtl.applets.repository.util.zeroIfNullOrBlank
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Evaluation
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.data.model.SommaireEtEvaluations
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import javax.inject.Inject

/**
 * Created by Sonphil on 22-08-18.
 */

class GradesDetailsViewModel @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val repository: EvaluationRepository,
    private val app: App
) : ViewModel(), LifecycleObserver {
    val cours = MutableLiveData<Cours>()
    private var summaryAndEvaluationsRes: LiveData<Resource<SommaireEtEvaluations>>? = null
    private val summaryAndEvaluationsMediatorLiveData: MediatorLiveData<Resource<SommaireEtEvaluations>> by lazy {
        MediatorLiveData<Resource<SommaireEtEvaluations>>()
    }
    val errorMessage: LiveData<Event<String?>> by lazy {
        Transformations.map(summaryAndEvaluationsMediatorLiveData) {
            if (it.status == Resource.ERROR) {
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
                EvaluationDetailItem(app.getString(R.string.label_group), cours.value?.groupe ?: ""),
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

        it?.takeIf { it.status != Resource.LOADING }?.data?.let {
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
        (it.status != Resource.LOADING && (it?.data?.evaluations == null || it.data?.evaluations?.isEmpty() == true))
    }

    fun getLoading(): LiveData<Boolean> = Transformations.map(summaryAndEvaluationsMediatorLiveData) {
        it.status == Resource.LOADING
    }

    private fun load() {
        cours.value?.let {
            summaryAndEvaluationsRes = repository.getSummaryAndEvaluations(
                    userCredentials,
                    it,
                    true
            ).apply {
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