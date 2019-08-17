package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.repository.signets.SeanceRepository
import model.Resource
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
        return Transformations.map(seanceRepository.getSeancesSession(
            userCredentials,
            session
        )) { seancesRes ->
            if (seancesRes.status == Resource.Status.ERROR) {
                Resource.error(
                    seancesRes.message ?: app.getString(R.string.error),
                    seancesRes.data
                )
            } else {
                seancesRes
            }
        }
    }
}