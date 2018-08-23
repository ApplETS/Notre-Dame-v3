package ca.etsmtl.etsmobile.presentation.grades

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.util.Event
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Evaluation
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.repository.data.repository.signets.EvaluationRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 22-08-18.
 */

class GradesDetailsViewModel @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val repository: EvaluationRepository
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
            addSource(summaryMediatorLiveData) { this.value = Event(it?.message) }
            addSource(evaluationsMediatorLiveData) { this.value = Event(it?.message) }
        }
    }

    fun getLoading(): LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(summaryMediatorLiveData) {
            this.value = evaluationsMediatorLiveData.value?.status == Resource.LOADING || it?.status == Resource.LOADING
        }

        addSource(evaluationsMediatorLiveData) {
            this.value = summaryMediatorLiveData.value?.status == Resource.LOADING || it?.status == Resource.LOADING
        }
    }

    fun getGradePercentage(): LiveData<String> = Transformations.map(summaryMediatorLiveData) {
        it.takeIf { it.status != Resource.LOADING }?.data?.scoreFinalSur100
    }

    fun getAveragePercentage(): LiveData<String> = Transformations.map(summaryMediatorLiveData) {
        it.takeIf { it.status != Resource.LOADING }?.data?.moyenneClasse
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
                    evaluationsMediatorLiveData.value = it
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