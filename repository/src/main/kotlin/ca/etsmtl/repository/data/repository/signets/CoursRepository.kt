package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.repository.data.api.response.mapper.toCoursEntity
import ca.etsmtl.repository.data.api.response.signets.ApiCours
import ca.etsmtl.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.repository.data.db.entity.mapper.toCours
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import retrofit2.Response
import javax.inject.Inject

/**
 * This repository provides access to the courses.
 */
class CoursRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val coursDao: CoursDao,
    private val evaluationRepository: EvaluationRepository
) : SignetsRepository(appExecutors) {
    /**
     * Returns the user's courses
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     * @return The courses
     */
    fun getCours(
        userCredentials: SignetsUserCredentials,
        shouldFetch: Boolean = true
    ): LiveData<Resource<List<Cours>>> {
        return object : NetworkBoundResource<List<Cours>, HashMap<ApiCours, SommaireElementsEvaluation>>(appExecutors) {
            override fun saveCallResult(item: HashMap<ApiCours, SommaireElementsEvaluation>) {
                item.takeIf { item.isNotEmpty() }?.let {
                    coursDao.deleteAll()
                    it.forEach {
                        coursDao.insert(it.key.toCoursEntity(it.value))
                    }
                }
            }

            override fun shouldFetch(data: List<Cours>?) = shouldFetch

            override fun loadFromDb(): LiveData<List<Cours>> {
                return Transformations.map(coursDao.getAll()) {
                    it?.toCours()
                }
            }

            override fun createCall(): LiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>> {
                return Transformations.switchMap(transformApiLiveData(api.listeCours(EtudiantRequestBody(userCredentials)))) {
                    if (it.isSuccessful) {
                        val liste = it.body?.data?.liste

                        when (liste) {
                            null -> MutableLiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>>().apply {
                                value = ApiResponse(Throwable(it.errorMessage))
                            }
                            // The courses don't includes the grades. We need to get the grade of every course.
                            else -> getEvaluationsForListOfCours(liste)
                        }
                    } else {
                        MutableLiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>>().apply {
                            value = ApiResponse(Throwable(it.errorMessage))
                        }
                    }
                }
            }

            /**
             * Returns a LiveData containing an [ApiResponse] containing a [HashMap] containing
             * [ApiCours]s and their [SommaireElementsEvaluation]
             *
             * This is performed by calling [EvaluationRepository.getEvaluationsSummary] for each
             * [ApiCours]. However, if one of the calls fails, a failed Api response will be
             * posted to the [LiveData]
             *
             * @param cours The courses
             */
            private fun getEvaluationsForListOfCours(cours: List<ApiCours>): LiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>> {
                val mediatorLiveData = MediatorLiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>>()
                val hashMap = HashMap<ApiCours, SommaireElementsEvaluation>()

                cours.forEach { apiCours ->
                    with(getEvaluationSummaryForCours(apiCours)) {
                        mediatorLiveData.addSource(this) {
                            it?.let {
                                when (it.status) {
                                    Resource.SUCCESS -> {
                                        hashMap[apiCours] = it.data!!
                                        if (hashMap.size == cours.size) {
                                            mediatorLiveData.value = ApiResponse(Response.success(hashMap))
                                        }
                                    }
                                    Resource.ERROR -> mediatorLiveData.value = ApiResponse(Throwable(it.message))
                                }

                                if (it.status != Resource.LOADING)
                                    mediatorLiveData.removeSource(this)
                            }
                        }
                    }
                }

                return mediatorLiveData
            }

            /**
             * Creates a call for fetching the summary of the evaluations for a given courses
             *
             * @param apiCours The courses
             */
            private fun getEvaluationSummaryForCours(apiCours: ApiCours): LiveData<Resource<SommaireElementsEvaluation>> {
                return with(Cours(
                        apiCours.sigle,
                        apiCours.groupe,
                        apiCours.session,
                        apiCours.programmeEtudes,
                        apiCours.cote,
                        "",
                        apiCours.nbCredits,
                        apiCours.titreCours
                )) {
                    evaluationRepository.getEvaluationsSummary(
                            userCredentials,
                            this,
                            true
                    )
                }
            }
        }.asLiveData()
    }
}