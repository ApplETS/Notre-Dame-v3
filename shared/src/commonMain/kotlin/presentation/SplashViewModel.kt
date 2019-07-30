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
    val navigateToDashboard = Channel<Unit>()
    val navigateToLogin = Channel<Unit>()
    val showLoading = Channel<Boolean>()
    val errorMessage = Channel<String>()

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
                navigateToDashboard.send(Unit)
            } else {
                navigateToLogin.send(Unit)
            }
        }
    }
}