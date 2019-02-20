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
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import ca.etsmtl.applets.etsmobile.util.toLocalizedString
import model.Resource
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import model.Cours
import model.Evaluation
import model.SommaireElementsEvaluation
import model.SommaireEtEvaluations
import java.util.Date
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
    val recyclerViewItems: LiveData<List<Group>> = Transformations.map(summaryAndEvaluations) {
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
                evaluation.dateCible?.let { Date(it.unixMillisLong).toLocalizedString() } ?: ""
            )
        )

        it?.takeIf { it.status != Resource.Status.LOADING }?.data?.let {
            val gradeAverageItem = it.sommaireElementsEvaluation.run {
                GradeAverageItem(
                    cours.value?.cote,
                    note,
                    noteSur,
                    noteSur100,
                    moyenneClasse,
                    moyenneClassePourcentage
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
                            it.note,
                            it.corrigeSur,
                            it.notePourcentage
                        )

                        val averageStr = String.format(
                            app.getString(R.string.text_grade_with_percentage),
                            it.moyenne,
                            it.corrigeSur,
                            it.moyennePourcentage
                        )

                        add(Section(getEvaluationDetailItems(grade, averageStr, it)))
                    }
                }

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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun refresh() = summaryAndEvaluations.refresh()
}