package domain

import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import model.Resource
import kotlin.coroutines.coroutineContext

/**
 * Created by Sonphil on 17-07-19.
 */

class LoginWithSavedCredentialsUseCase @Inject constructor(
    private val fetchSavedUserCredentialsUseCase: FetchSavedUserCredentialsUseCase,
    private val checkUserCredentialsAreValidUseCase: CheckUserCredentialsAreValidUseCase
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return withContext(coroutineContext) {
            flow<Resource<Boolean>> {
                emit(Resource.loading<Boolean>(null))

                val credentials = fetchSavedUserCredentialsUseCase()

                if (credentials == null) {
                    emit(Resource.error("", false))
                } else {
                    emitAll(checkUserCredentialsAreValidUseCase(credentials))
                }
            }
        }
    }
}