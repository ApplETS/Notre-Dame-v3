package ca.etsmtl.etsmobile.presentation.gradesdetails

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.util.Event
import ca.etsmtl.etsmobile.util.isDeviceConnected
import ca.etsmtl.etsmobile.util.zeroIfNullOrBlank
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Evaluation
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.repository.data.repository.signets.EvaluationRepository
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
    private val summaryMediatorLiveData: MediatorLiveData<Resource<SommaireElementsEvaluation>> by lazy {
        MediatorLiveData<Resource<SommaireElementsEvaluation>>()
    }
    private var summaryRes: LiveData<Resource<SommaireElementsEvaluation>>? = null
    private val evaluationsMediatorLiveData: MediatorLiveData<Resource<List<Evaluation>>> by lazy {
        MediatorLiveData<Resource<List<Evaluation>>>()
    }
    private var evaluationsRes: LiveData<Resource<List<Evaluation>>>? = null
    val errorMessage: LiveData<Event<String?>> by lazy {
        MediatorLiveData<Event<String?>>().apply {
            fun displayErrorMessage(res: Resource<*>?) {
                if (res?.status == Resource.ERROR) {
                    this.value = when {
                        !app.isDeviceConnected() -> {
                            Event(app.getString(R.string.error_no_internet_connection))
                        }
                        else -> Event(app.getString(R.string.error_failed_to_retrieve_data_course))
                    }
                }
            }

            addSource(summaryMediatorLiveData) { displayErrorMessage(it) }
            addSource(evaluationsMediatorLiveData) { displayErrorMessage(it) }
        }
    }
    private val gradeAverageItem: LiveData<GradeAverageItem> = Transformations.map(summaryMediatorLiveData) {
        it?.takeIf { it.status != Resource.LOADING }?.data?.let {
            GradeAverageItem(
                    cours.value?.cote,
                    it.scoreFinalSur100.zeroIfNullOrBlank(),
                    it.moyenneClasse.zeroIfNullOrBlank()
            )
        }
    }
    private val summaryDetailsItems: LiveData<List<EvaluationDetailItem>> = Transformations.map(summaryMediatorLiveData) {
        it?.takeIf { it.status != Resource.LOADING }?.data?.let {
            listOf(
                    EvaluationDetailItem(app.getString(R.string.label_median), it.medianeClasse),
                    EvaluationDetailItem(app.getString(R.string.label_standard_deviation), it.ecartTypeClasse),
                    EvaluationDetailItem(app.getString(R.string.label_percentile_rank), it.rangCentileClasse)
            )
        }
    }
    private val evaluationGroups: LiveData<List<ExpandableGroup>> = Transformations.map(evaluationsMediatorLiveData) {
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
        it?.takeIf { it.status != Resource.LOADING }?.data?.map {
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
    }
    val recyclerViewItems: LiveData<List<Group>> = MediatorLiveData<List<Group>>().apply {
        fun createItemsList(
            gradeAverageItem: GradeAverageItem?,
            summaryDetailsItems: List<EvaluationDetailItem>?,
            evaluationGroups: List<ExpandableGroup>?
        ): List<Group> {
            return ArrayList<Group>().apply {
                (gradeAverageItem as? Group)?.let { add(it) }

                summaryDetailsItems?.let {
                    SectionTitleItem(app.getString(R.string.title_section_summary))
                    addAll(summaryDetailsItems)
                }

                evaluationGroups?.let {
                    SectionTitleItem(app.getString(R.string.title_section_evaluations))
                    addAll(it)
                }
            }
        }

        addSource(gradeAverageItem) {
            this.value = createItemsList(it, summaryDetailsItems.value, evaluationGroups.value)
        }

        addSource(summaryDetailsItems) {
            this.value = createItemsList(gradeAverageItem.value, it, evaluationGroups.value)
        }

        addSource(evaluationGroups) {
            this.value = createItemsList(gradeAverageItem.value, summaryDetailsItems.value, it)
        }
    }

    fun getLoading(): LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun areSourcesLoading() = evaluationsMediatorLiveData.value?.status == Resource.LOADING
                || summaryMediatorLiveData.value?.status == Resource.LOADING

        addSource(summaryMediatorLiveData) { this.value = areSourcesLoading() }
        addSource(evaluationsMediatorLiveData) { this.value = areSourcesLoading() }
    }

    private fun load() {
        cours.value?.let {
            summaryRes = repository.getEvaluationsSummary(userCredentials, it, true).apply {
                summaryMediatorLiveData.addSource(this) {
                    summaryMediatorLiveData.value = it
                }
            }

            evaluationsRes = repository.getEvaluations(userCredentials, it, true).apply {
                evaluationsMediatorLiveData.addSource(this) {
                    evaluationsMediatorLiveData.value = it?.apply { data?.filter { it.publie } }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun refresh() {
        summaryRes?.let { summaryMediatorLiveData.removeSource(it) }
        summaryRes = null

        evaluationsRes?.let { evaluationsMediatorLiveData.removeSource(it) }
        evaluationsRes = null

        load()
    }
}