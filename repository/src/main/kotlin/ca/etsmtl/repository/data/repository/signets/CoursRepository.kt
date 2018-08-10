package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
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

// TODO: Test...
class CoursRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val coursDao: CoursDao,
    private val evaluationRepository: EvaluationRepository
) : SignetsRepository(appExecutors) {
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
                    it.toCours()
                }
            }

            override fun createCall(): LiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>> {
                return Transformations.switchMap(transformApiLiveData(api.listeCours(userCredentials))) {
                    val mediatorLiveData = MediatorLiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>>()

                    if (it.isSuccessful) {
                        val liste = it.body?.data?.liste

                        when (liste) {
                            null -> mediatorLiveData.value = ApiResponse(Throwable(it.errorMessage))
                            else -> getEvaluationsForListOfCours(liste, mediatorLiveData)
                        }
                    } else {
                        mediatorLiveData.value = ApiResponse(Throwable(it.errorMessage))
                    }

                    mediatorLiveData
                }
            }

            private fun getEvaluationsForListOfCours(cours: List<ApiCours>, mediatorLiveData: MediatorLiveData<ApiResponse<HashMap<ApiCours, SommaireElementsEvaluation>>>) {
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
            }

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