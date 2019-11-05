package ca.etsmtl.applets.etsmobile.presentation.gradesdetails

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchGradesDetailsUseCase
import ca.etsmtl.applets.etsmobile.extension.toLocalizedString
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import ca.etsmtl.applets.repository.util.zeroIfNullOrBlank
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import model.Cours
import model.Evaluation
import model.Resource
import model.SommaireElementsEvaluation
import model.SommaireEtEvaluations
import utils.date.toJvmDate
import javax.inject.Inject

/**
 * Created by Sonphil on 22-08-18.
 */

class GradesDetailsViewModel @Inject constructor(
    private val fetchGradesDetailsUseCase: FetchGradesDetailsUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    val cours = MutableLiveData<Cours>()
    private val summaryAndEvaluations = object : RefreshableLiveData<Resource<SommaireEtEvaluations>>() {
        override fun updateSource(): LiveData<Resource<SommaireEtEvaluations>> {
            return Transformations.switchMap(cours) {
                fetchGradesDetailsUseCase(it)
            }
        }
    }
    val errorMessage: LiveData<Event<String?>> = summaryAndEvaluations
        .nonNull()
        .map {
            Event(it.message)
        }

    private fun SommaireElementsEvaluation.createSummaryItems() = listOf(
        EvaluationDetailItem(app.getString(R.string.label_median), medianeClasse),
        EvaluationDetailItem(app.getString(R.string.label_standard_deviation), ecartTypeClasse),
        EvaluationDetailItem(app.getString(R.string.label_percentile_rank), rangCentileClasse)
    )

    private fun createEvaluationDetailItems(grade: String, average: String, evaluation: Evaluation) = listOf(
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
            evaluation.dateCible?.toJvmDate()?.toLocalizedString() ?: ""
        )
    )

    private fun SommaireEtEvaluations.createEvaluationsItems() = this
        .evaluations
        .map { evaluation ->
            evaluation.notePourcentage = evaluation.notePourcentage.zeroIfNullOrBlank()

            ExpandableGroup(EvaluationHeaderItem(evaluation)).apply {
                val grade = if (evaluation.note != null) String.format(
                    app.getString(R.string.text_grade_with_percentage),
                    evaluation.note,
                    evaluation.corrigeSur,
                    evaluation.notePourcentage
                ) else {
                    ""
                }

                val averageStr = if (evaluation.moyenne != null) String.format(
                    app.getString(R.string.text_grade_with_percentage),
                    evaluation.moyenne,
                    evaluation.corrigeSur,
                    evaluation.moyennePourcentage
                ) else {
                    ""
                }

                add(Section(createEvaluationDetailItems(grade, averageStr, evaluation)))
            }
        }

    val detailsListItems: LiveData<List<Group>> = Transformations.map(summaryAndEvaluations) {
        it?.takeIf { it.status != Resource.Status.LOADING }?.data?.let { sommaireEtEvaluations ->
            val gradeAndAverageItem = sommaireEtEvaluations.sommaireElementsEvaluation.run {
                GradeAndAverageItem(
                    cours.value?.cote,
                    note.zeroIfNullOrBlank(),
                    noteSur.zeroIfNullOrBlank(),
                    noteSur100.zeroIfNullOrBlank(),
                    moyenneClasse.zeroIfNullOrBlank(),
                    moyenneClassePourcentage.zeroIfNullOrBlank()
                )
            }

            mutableListOf<Group>(gradeAndAverageItem).apply {
                add(SectionTitleItem(app.getString(R.string.title_section_summary)))

                val summaryItems = sommaireEtEvaluations
                    .sommaireElementsEvaluation
                    .createSummaryItems()
                addAll(summaryItems)

                add(SectionTitleItem(app.getString(R.string.title_section_evaluations)))

                val evaluationsItems = sommaireEtEvaluations
                    .createEvaluationsItems()
                addAll(evaluationsItems)
            }
        }
    }
    val showEmptyView: LiveData<Boolean> = summaryAndEvaluations
        .nonNull()
        .map {
            (it.status != Resource.Status.LOADING && (it?.data?.evaluations == null || it.data?.evaluations?.isEmpty() == true))
        }

    val loading: LiveData<Boolean> = summaryAndEvaluations
        .map {
            it == null || it.status == Resource.Status.LOADING
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refreshIfContentNotLoaded() = summaryAndEvaluations.refreshIfValueIsNull()

    fun refresh() = summaryAndEvaluations.refresh()
}