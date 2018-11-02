package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.SessionRepository
import java.util.Date
import javax.inject.Inject

/**
 * Created by Sonphil on 13-10-18.
 */

class FetchCurrentSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val app: App
) {
    operator fun invoke(userCredentials: SignetsUserCredentials): LiveData<Resource<Session>> {
        return Transformations.map(sessionRepository.getSessions(userCredentials) { true }) {
            val currentSession = it.data.orEmpty().find {
                //dateDebut et dateFin sont des Timestamp en s
                //Date().time est en ms
                Date().time in (it.dateDebut*1000)..(it.dateFin*1000)
            }

            if (it.status == Resource.Status.LOADING) {
                Resource.loading(currentSession)
            } else if (it.status == Resource.Status.ERROR || currentSession == null) {
                Resource.error(it.message ?: app.getString(R.string.error), currentSession)
            } else {
                Resource.success(currentSession)
            }
        }
    }
}