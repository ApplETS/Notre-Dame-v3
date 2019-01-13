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
            var completedSeancesFetch = 0
            val mediatorLiveData = MediatorLiveData<Resource<List<Seance>>>()

            fun fetchSeancesFromSession(session: Session){
                mediatorLiveData.addSource(
                    seanceRepository.getSeancesSession(
                        userCredentials,null, session, false
                    )
                ){ res ->
                    when(res.status){
                        Resource.Status.LOADING ->
                            mediatorLiveData.value = Resource.loading(mediatorLiveData.value?.data)
                        Resource.Status.ERROR -> { //TODO: Traiter le message d'erreur d'aucun cours
                            ++completedSeancesFetch
                            mediatorLiveData.value = Resource.error(
                                res.message ?: app.getString(R.string.error),
                                mediatorLiveData.value?.data
                            )
                        }
                        Resource.Status.SUCCESS ->{
                            ++completedSeancesFetch

                            if (res.data == null){
                                mediatorLiveData.value = Resource.error(app.getString(R.string.error), null)
                            } else {
                                val seances = mutableListOf<Seance>()
                                mediatorLiveData.value?.data?.let {
                                    seances.addAll(it)
                                }
                                seances.addAll(res.data!!)

                                mediatorLiveData.value = if (completedSeancesFetch == sessions.size)
                                    Resource.success(seances) else Resource.loading(seances)
                            }
                        }
                    }
                }
            }

            when (resSessions.status){
                Resource.Status.ERROR -> mediatorLiveData.value =
                    Resource.error(resSessions.message ?: app.getString(R.string.error), null)
                Resource.Status.LOADING -> mediatorLiveData.value = Resource.loading(null)
                Resource.Status.SUCCESS -> {
                    sessions.forEach { fetchSeancesFromSession(it) }
                }
            }

            mediatorLiveData
        }
    }
}