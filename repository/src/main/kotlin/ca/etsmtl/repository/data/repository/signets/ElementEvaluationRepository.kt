package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Cours
import ca.etsmtl.repository.data.model.signets.Evaluation
import ca.etsmtl.repository.data.model.signets.ListeDesElementsEvaluation
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * This repository provides access to the student's evaluations (exams, assignments, etc.).
 *
 * Created by Sonphil on 24-06-18.
 */

class ElementEvaluationRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: EvaluationDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns list of the student's evaluations (exams, assignments, etc.) for a given course
     *
     * @param userCredentials The user's credentials
     * @param cours The course
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @return A list of the user's sessions
     */
    fun getEvaluations(
        userCredentials: SignetsUserCredentials,
        cours: Cours,
        shouldFetch: Boolean
    ): LiveData<Resource<List<Evaluation>>> = object : NetworkBoundResource<List<Evaluation>, SignetsModel<ListeDesElementsEvaluation>>(appExecutors) {
        override fun saveCallResult(item: SignetsModel<ListeDesElementsEvaluation>) {
            item.data?.liste?.let { dao.insertAll(*it.toTypedArray()) }
        }

        override fun shouldFetch(data: List<Evaluation>?): Boolean = shouldFetch

        override fun loadFromDb(): LiveData<List<Evaluation>> = dao.getAll()

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