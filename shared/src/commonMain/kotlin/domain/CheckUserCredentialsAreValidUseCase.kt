package domain

import data.repository.LoginRepository
import di.Inject
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.withContext
import model.Resource
import model.SignetsUserCredentials
import kotlin.coroutines.coroutineContext

/**
 * Created by Sonphil on 17-07-19.
 */

class CheckUserCredentialsAreValidUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(credentials: SignetsUserCredentials): ReceiveChannel<Resource<Boolean>> {
        return withContext(coroutineContext) {
            produce {
                send(Resource.loading<Boolean>(null))

                try {
                    repository.authenticate(credentials)

                    send(Resource.success(true))
                } catch (cause: Throwable) {
                    send(Resource.error(cause.message ?: "", false))
                }
            }
        }
    }
}