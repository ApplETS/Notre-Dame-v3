package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.SessionRepository
import javax.inject.Inject

/**
Created by mykaelll87 on 17/11/18
 */
class FetchSessionsUseCase @Inject constructor(
    private val userCredentials: SignetsUserCredentials,
    private val sessionRepository: SessionRepository,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<List<Session>>> {
        return Transformations.map(sessionRepository.getSessions(userCredentials) { true }) {
            when {
                it.status == Resource.Status.LOADING -> Resource.loading(it.data.orEmpty())
                it.status == Resource.Status.ERROR -> Resource.error(it.message ?: app.getString(R.string.error), it.data.orEmpty())
                else -> Resource.success(it.data.orEmpty())
            }
        }
    }
}