package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.SeanceRepository
import model.Seance
import model.Session
import model.SignetsUserCredentials
import javax.inject.Inject

/**
Created by mykaelll87 on 17/11/18
 */
class FetchSessionSeancesUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val seanceRepository: SeanceRepository,
    private val app: App
) {
    operator fun invoke(session: Session): LiveData<Resource<List<Seance>>> {
        return Transformations.switchMap(seanceRepository.getSeancesSession(userCredentials,
            null, session, true)) { res ->
            val seances = res.data
            val mediatorLiveData = MediatorLiveData<Resource<List<Seance>>>()

            when (res.status) {
                Resource.Status.ERROR -> {
                        mediatorLiveData.value =
                            Resource.error(res.message ?: app.getString(R.string.error), emptyList())
                }
                Resource.Status.LOADING -> mediatorLiveData.value = Resource.loading(null)
                Resource.Status.SUCCESS -> {
                    if (seances == null) {
                        mediatorLiveData.value = Resource.error(app.getString(R.string.error), null)
                    } else {
                        mediatorLiveData.value = Resource.success(seances)
                    }
                }
            }
            mediatorLiveData
        }
    }
}