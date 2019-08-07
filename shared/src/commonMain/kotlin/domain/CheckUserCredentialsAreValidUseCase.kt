package domain

import data.repository.LoginRepository
import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Resource
import model.UniversalCode

/**
 * Created by Sonphil on 17-07-19.
 */

class CheckUserCredentialsAreValidUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(universalCode: UniversalCode, password: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.loading<Boolean>(null))

            try {
                repository.authenticate(universalCode, password)

                emit(Resource.success(true))
            } catch (cause: Throwable) {
                emit(Resource.error(cause.message ?: "", false))
            }
        }
    }
}