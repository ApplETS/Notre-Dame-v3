package domain

import data.repository.LoginRepository
import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Resource
import model.SignetsUserCredentials

/**
 * Created by Sonphil on 17-07-19.
 */

class CheckUserCredentialsAreValidUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(credentials: SignetsUserCredentials): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.loading<Boolean>(null))

            try {
                repository.authenticate(credentials)

                emit(Resource.success(true))
            } catch (cause: Throwable) {
                emit(Resource.error(cause.message ?: "", false))
            }
        }
    }
}