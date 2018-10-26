package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Seance
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.SeanceRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 13-10-18.
 */

class FetchCurrentSessionSeancesUseCase @Inject constructor(
    private val fetchCurrentSessionUseCase: FetchCurrentSessionUseCase,
    private val seanceRepository: SeanceRepository,
    private val app: App
) {
    operator fun invoke(userCredentials: SignetsUserCredentials): LiveData<Resource<List<Seance>>> {
        return Transformations.switchMap(fetchCurrentSessionUseCase(userCredentials)) {
            MediatorLiveData<Resource<List<Seance>>>().apply {
                val session = it.data

                if (it.status == Resource.Status.ERROR || session == null) {
                    value = Resource.error(
                            it.message ?: app.getString(R.string.error),
                            null
                    )
                } else if (it.status == Resource.Status.LOADING) {
                    value = Resource.loading(null)
                } else {
                    val seances = seanceRepository.getSeancesSession(
                            userCredentials,
                            null,
                            session,
                            true
                    )

                    addSource(seances) {
                        value = it

                        it?.let {
                            removeSource(seances)
                        }
                    }
                }
            }
        }
    }
}