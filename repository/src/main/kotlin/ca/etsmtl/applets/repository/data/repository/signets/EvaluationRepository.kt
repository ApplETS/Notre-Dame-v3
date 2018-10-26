package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeDesElementsEvaluationRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toEvaluationEntities
import ca.etsmtl.applets.repository.data.api.response.mapper.toSommaireEvaluationEntity
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SommaireElementsEvaluationDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toEvaluations
import ca.etsmtl.applets.repository.data.db.entity.mapper.toSommaireEvaluation
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Evaluation
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.data.model.SommaireEtEvaluations
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

    /**
     * Returns a summary of the [Evaluation]s for a given [Cours]
     *
     * @param userCredentials The user's credentials
     * @param cours The course
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getEvaluationsSummary(
        userCredentials: SignetsUserCredentials,
        cours: Cours,
        shouldFetch: Boolean = true
    ): LiveData<Resource<SommaireElementsEvaluation>> = object : SignetsNetworkBoundResource<SommaireElementsEvaluation, ApiListeDesElementsEvaluation>(appExecutors) {
        override fun saveSignetsData(item: ApiListeDesElementsEvaluation) {
            sommaireElementsEvaluationDao.clearAndInsertBySigleCoursAndSession(
                    cours.sigle,
                    cours.session,
                    item.toSommaireEvaluationEntity(cours)
            )
        }

        override fun shouldFetch(data: SommaireElementsEvaluation?): Boolean = shouldFetch

        override fun loadFromDb(): LiveData<SommaireElementsEvaluation> {
            return Transformations.map(sommaireElementsEvaluationDao.getBySigleCoursAndSession(cours.sigle, cours.session)) {
                it?.toSommaireEvaluation()
            }
        }

        override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesElementsEvaluation>>> {
            return api.listeDesElementsEvaluation(
                    ListeDesElementsEvaluationRequestBody(
                            userCredentials.codeAccesUniversel,
                            userCredentials.motPasse,
                            cours.sigle,
                            cours.groupe,
                            cours.session
                    )
            )
        }
    }.asLiveData()

    /**
     * Returns [SommaireEtEvaluations] which contains the summary and the list of evaluations for a
     * given [Cours]
     *
     * Call this function to obtain both with one HTTP request
     *
     * @param userCredentials The user's credentials
     * @param cours The course
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getSummaryAndEvaluations(
        userCredentials: SignetsUserCredentials,
        cours: Cours,
        shouldFetch: Boolean = true
    ): LiveData<Resource<SommaireEtEvaluations>> = object : SignetsNetworkBoundResource<SommaireEtEvaluations, ApiListeDesElementsEvaluation>(appExecutors) {
        override fun saveSignetsData(item: ApiListeDesElementsEvaluation) {
            sommaireElementsEvaluationDao.clearAndInsertBySigleCoursAndSession(
                    cours.sigle,
                    cours.session,
                    item.toSommaireEvaluationEntity(cours)
            )

            evaluationDao.clearAndInsertByCoursGroupeAndSession(
                    cours.sigle,
                    cours.groupe,
                    cours.session,
                    item.toEvaluationEntities(cours)
            )
        }

        override fun shouldFetch(data: SommaireEtEvaluations?) = shouldFetch

        override fun loadFromDb(): LiveData<SommaireEtEvaluations> {
            return Transformations.switchMap(sommaireElementsEvaluationDao.getBySigleCoursAndSession(cours.sigle, cours.session)) { sommaire ->
                if (sommaire == null) {
                    MutableLiveData<SommaireEtEvaluations>().apply { value = null }
                } else {
                    Transformations.map(evaluationDao.getByCoursGroupeAndSession(
                            cours.sigle,
                            cours.groupe,
                            cours.session
                    )) {
                        SommaireEtEvaluations(sommaire.toSommaireEvaluation(), it.toEvaluations())
                    }
                }
            }
        }

        override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesElementsEvaluation>>> {
            return api.listeDesElementsEvaluation(
                    ListeDesElementsEvaluationRequestBody(
                            userCredentials.codeAccesUniversel,
                            userCredentials.motPasse,
                            cours.sigle,
                            cours.groupe,
                            cours.session
                    )
            )
        }
    }.asLiveData()
}