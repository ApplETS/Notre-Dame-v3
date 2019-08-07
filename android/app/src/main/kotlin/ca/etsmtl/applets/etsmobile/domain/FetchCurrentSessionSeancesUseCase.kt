package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.repository.signets.SeanceRepository
import model.Resource
import model.Seance
import model.Session
import model.UserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 13-10-18.
 */

class FetchCurrentSessionSeancesUseCase @Inject constructor(
    private val userCredentials: UserCredentials,
    private val fetchCurrentSessionUseCase: FetchCurrentSessionUseCase,
    private val seanceRepository: SeanceRepository,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<List<Seance>>> {
        return Transformations.switchMap(fetchCurrentSessionUseCase()) { res ->
            val session = res.data

            if (session == null) {
                getEmptyResult(res)
            } else {
                seanceRepository.getSeancesSession(userCredentials, session)
            }
        }
    }

    private fun getEmptyResult(sessionResult: Resource<Session>) = MutableLiveData<Resource<List<Seance>>>()
        .apply {
            value = if (sessionResult.status == Resource.Status.LOADING) {
                Resource.loading(emptyList())
            } else {
                Resource.error(sessionResult.message ?: app.getString(R.string.error), emptyList())
            }
        }
}