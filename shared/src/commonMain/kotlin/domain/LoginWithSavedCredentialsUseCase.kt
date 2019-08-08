package domain

import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import model.Resource
import kotlin.coroutines.coroutineContext

/**
 * Created by Sonphil on 17-07-19.
 */

class LoginWithSavedCredentialsUseCase @Inject constructor(
    private val fetchSavedUserCredentialsUseCase: FetchSavedUserCredentialsUseCase
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return withContext(coroutineContext) {
            flow<Resource<Boolean>> {
                emit(Resource.loading(null))

                val credentials = fetchSavedUserCredentialsUseCase()

                if (credentials == null) {
                    emit(Resource.error("", false))
                } else {
                    emit(Resource.success(true))
                }
            }
        }
    }
}