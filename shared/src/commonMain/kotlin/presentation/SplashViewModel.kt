package presentation

import di.Inject
import domain.LoginWithSavedCredentials
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Resource

/**
 * Created by Sonphil on 28-02-18.
 */

class SplashViewModel @Inject constructor(
    private val loginWithSavedCredentials: LoginWithSavedCredentials
) : ViewModel() {
    val navigateToDashboard = Channel<Event<Unit>>()
    val navigateToLogin = Channel<Event<Unit>>()
    val showLoading = Channel<Boolean>()
    val errorMessage = Channel<Event<String>>()

    fun submitSavedCredentials() {
        vmScope.launch {
            loginWithSavedCredentials().collect { res ->
                handleLoginResult(res)
            }
        }
    }

    private suspend fun handleLoginResult(res: Resource<Boolean>) {
        showLoading.send(res.status == Resource.Status.LOADING)

        if (res.status != Resource.Status.LOADING) {
            if (res.data == true) {
                navigateToDashboard.send(Event(Unit))
            } else {
                navigateToLogin.send(Event(Unit))
            }
        }
    }
}