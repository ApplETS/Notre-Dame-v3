package domain

import di.Inject
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.withContext
import model.Resource
import kotlin.coroutines.coroutineContext

/**
 * Created by Sonphil on 17-07-19.
 */

class LoginWithSavedCredentials @Inject constructor(
    private val fetchSavedUserCredentialsUseCase: FetchSavedUserCredentialsUseCase,
    private val checkUserCredentialsAreValidUseCase: CheckUserCredentialsAreValidUseCase
) {
    suspend operator fun invoke(): ReceiveChannel<Resource<Boolean>> {
        return withContext(coroutineContext) {
            produce {
                send(Resource.loading<Boolean>(null))

                val credentials = fetchSavedUserCredentialsUseCase()

                if (credentials == null) {
                    send(Resource.error("", false))
                } else {
                    for (result in checkUserCredentialsAreValidUseCase(credentials)) {
                        if (result.status != Resource.Status.LOADING) {
                            send(result)
                            close()
                            break
                        }
                    }
                }
            }
        }
    }
}