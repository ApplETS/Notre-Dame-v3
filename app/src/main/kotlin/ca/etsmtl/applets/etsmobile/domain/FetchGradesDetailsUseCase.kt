package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.model.SommaireEtEvaluations
import ca.etsmtl.applets.repository.data.repository.signets.EvaluationRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 11-10-18.
 */

class FetchGradesDetailsUseCase @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val repository: EvaluationRepository,
    private val app: App
) {
    operator fun invoke(cours: Cours): LiveData<Resource<SommaireEtEvaluations>> = repository.getSummaryAndEvaluations(
            userCredentials,
            cours,
            true
    )
}