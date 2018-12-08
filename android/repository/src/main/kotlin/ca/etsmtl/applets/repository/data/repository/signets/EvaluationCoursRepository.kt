package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeEvaluationCoursRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toEvaluationCoursEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeEvaluationCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationCoursDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toEvaluationCours
import ca.etsmtl.applets.repository.data.model.*
import javax.inject.Inject

/**
 * This repository provides a list of course's evaluations (feedback given to teachers about their courses)
 */
class EvaluationCoursRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val evaluationCoursDao: EvaluationCoursDao
): SignetsRepository(appExecutors) {
    fun getEvaluationCours(
        userCredentials: SignetsUserCredentials,
        cours: Cours,
        shouldFetch: Boolean = true
    ): LiveData<Resource<List<EvaluationCours>>> = object : SignetsNetworkBoundResource<List<EvaluationCours>, ApiListeEvaluationCours>(appExecutors) {
        override fun saveSignetsData(item: ApiListeEvaluationCours) {
            evaluationCoursDao.deleteBySession(cours.session)
            evaluationCoursDao.insertAll(item.toEvaluationCoursEntities(cours))
        }

        override fun shouldFetch(data: List<EvaluationCours>?) = shouldFetch

        override fun loadFromDb(): LiveData<List<EvaluationCours>> = Transformations.map(evaluationCoursDao.getEvaluationCoursBySession(cours.session)) {
            it.toEvaluationCours()
        }

        override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeEvaluationCours>>> {
            return api.listeEvaluationCours(ListeEvaluationCoursRequestBody(
                userCredentials.codeAccesUniversel,
                userCredentials.motPasse,
                cours.session
            ))
        }
    }.asLiveData()
}