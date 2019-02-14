package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Seance
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.SeanceRepository
import javax.inject.Inject

/**
Created by mykaelll87 on 13/01/19
 */
class FetchSeancesUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val seanceRepository: SeanceRepository,
    private val fetchSessionsUseCase: FetchSessionsUseCase,
    private val app: App
) {
    operator fun  invoke(): LiveData<Resource<List<Seance>>>{
        return Transformations.switchMap(fetchSessionsUseCase()){ resSessions->
            val sessions = resSessions.data.orEmpty()
            val completedSeancesFetch = mutableSetOf<Session>()
            val mediatorLiveData = MediatorLiveData<Resource<List<Seance>>>()
            var latestError: String? = null

            fun updateMediatorLiveData(res: Resource<List<Seance>>){

                val seances = mutableListOf<Seance>()
                mediatorLiveData.value?.data?.let {
                    seances.addAll(it)
                }
                seances.addAll(res.data!!)

                seances.sortBy { it.dateDebut }

                if (completedSeancesFetch.size != sessions.size){
                    mediatorLiveData.value = Resource.loading(seances)
                } else if (latestError != null && latestError != ""){
                    mediatorLiveData.value = Resource.error(latestError!!, seances)
                } else {
                    mediatorLiveData.value = Resource.success(seances)
                }
            }

            fun fetchSeancesFromSession(session: Session){
                mediatorLiveData.addSource(
                    seanceRepository.getSeancesSession(
                        userCredentials,null, session, true
                    )
                ){ res ->
                    when(res.status){
                        Resource.Status.LOADING ->
                            mediatorLiveData.value = Resource.loading(mediatorLiveData.value?.data.orEmpty())
                        Resource.Status.ERROR -> {
                            completedSeancesFetch.add(session)
                            latestError = res.message
                            updateMediatorLiveData(res)

                        }
                        Resource.Status.SUCCESS ->{
                            completedSeancesFetch.add(session)
                            updateMediatorLiveData(res)
                        }
                    }
                }
            }

            when (resSessions.status){
                Resource.Status.ERROR -> mediatorLiveData.value =
                    Resource.error(resSessions.message ?: app.getString(R.string.error), emptyList())
                Resource.Status.LOADING -> mediatorLiveData.value = Resource.loading(emptyList())
                Resource.Status.SUCCESS -> {
                    sessions.forEach { fetchSeancesFromSession(it) }
                }
            }

            mediatorLiveData
        }
    }
}