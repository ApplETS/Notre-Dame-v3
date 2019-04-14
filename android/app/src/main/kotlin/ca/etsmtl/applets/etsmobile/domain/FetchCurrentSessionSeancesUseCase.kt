package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.SeanceRepository
import model.Seance
import model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 13-10-18.
 */

class FetchCurrentSessionSeancesUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val fetchCurrentSessionUseCase: FetchCurrentSessionUseCase,
    private val seanceRepository: SeanceRepository,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<List<Seance>>> {
        return Transformations.switchMap(fetchCurrentSessionUseCase()) { res ->
            val session = res.data
            val mediatorLiveData = MediatorLiveData<Resource<List<Seance>>>()

            fun fetchSeancesFromSession() {
                if (session == null) {
                    mediatorLiveData.value = Resource.error(app.getString(R.string.error), session)
                } else {
                    mediatorLiveData.addSource(
                        seanceRepository.getSeancesSession(
                            userCredentials,
                            null,
                            session,
                            true
                        )
                    ) {
                        val seances = it.data

                        when (it.status) {
                            Resource.Status.LOADING ->
                                mediatorLiveData.value = Resource.loading(seances)
                            Resource.Status.ERROR ->
                                mediatorLiveData.value =
                                    Resource.error(it.message ?: app.getString(R.string.error), seances)
                            Resource.Status.SUCCESS -> {
                                if (seances == null) {
                                    mediatorLiveData.value =
                                        Resource.error(app.getString(R.string.error), seances)
                                } else {
                                    mediatorLiveData.value = Resource.success(seances)
                                }
                            }
                        }
                    }
                }
            }

            when (res.status) {
                Resource.Status.ERROR -> mediatorLiveData.value =
                    Resource.error(res.message ?: app.getString(R.string.error), null)
                Resource.Status.SUCCESS -> fetchSeancesFromSession()
                Resource.Status.LOADING -> mediatorLiveData.value = Resource.loading(null)
            }
            mediatorLiveData
        }
    }
}