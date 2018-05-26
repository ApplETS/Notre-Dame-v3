package ca.etsmtl.etsmobile.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile.AppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.EvaluationDao
import ca.etsmtl.etsmobile.data.db.dao.SommaireElementsEvaluationDao
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.Cours
import ca.etsmtl.etsmobile.data.model.signets.Evaluation
import ca.etsmtl.etsmobile.data.model.signets.ListeDesElementsEvaluation
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * This repository provide a list of the the student's evaluations (exams, assignments, etc.) for a
 * given course. It can also provide a summary for a given course.
 *
 * Created by Sonphil on 26-05-18.
 */

class EvaluationRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val evaluationDao: EvaluationDao,
    private val sommaireElementsEvaluationDao: SommaireElementsEvaluationDao
) : SignetsRepository(appExecutors) {
    fun getEvaluations(
        userCredentials: SignetsUserCredentials,
        cours: Cours,
        shouldFetch: Boolean
    ): LiveData<Resource<List<Evaluation>>> = object : NetworkBoundResource<List<Evaluation>, SignetsModel<ListeDesElementsEvaluation>>(appExecutors) {
        override fun saveCallResult(item: SignetsModel<ListeDesElementsEvaluation>) {
            item.data?.liste?.let { evaluationDao.insertAll(*it.toTypedArray()) }
        }

        override fun shouldFetch(data: List<Evaluation>?): Boolean = shouldFetch

        override fun loadFromDb(): LiveData<List<Evaluation>> = evaluationDao.getAll()

        override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeDesElementsEvaluation>>> {
            return transformApiLiveData(api.listeDesElementsEvaluation(
                    userCredentials.codeAccesUniversel,
                    userCredentials.motPasse,
                    cours.sigle,
                    cours.groupe,
                    cours.session
            ))
        }
    }.asLiveData()
}