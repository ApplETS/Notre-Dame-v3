package presentation

import di.Inject
import domain.CheckUserCredentialsAreValidUseCase
import domain.LoginWithSavedCredentials
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Resource
import model.SignetsUserCredentials
import model.UniversalCode

/**
 * Created by Sonphil on 18-07-19.
 */

class LoginViewModel @Inject constructor(
    private val checkUserCredentialsAreValidUseCase: CheckUserCredentialsAreValidUseCase,
    private val loginWithSavedCredentials: LoginWithSavedCredentials
) : ViewModel() {
    private var universalCode: UniversalCode = UniversalCode("")
    private var password: String = ""
    val navigateToDashboard = Channel<Event<Unit>>()
    val displayUniversalCodeDialog = Channel<Boolean>()
    val showLoading = Channel<Boolean>()
    val universalCodeErrorMessage = Channel<Event<String>>()
    val passwordErrorMessage = Channel<Event<String>>()
    val loginErrorMessage = Channel<Event<String>>()
    val hideKeyboard = Channel<Unit>()

    fun submitSavedCredentials() {
        vmScope.launch {
            loginWithSavedCredentials().collect { res ->
                handleLoginResult(res)
            }
        }
    }

    /**
     * Submits the user's credentials
     */
    fun submitCredentials() {
        vmScope.launch {
            hideKeyboard.send(Unit)
            val credentials = SignetsUserCredentials(universalCode, password)
            checkUserCredentialsAreValidUseCase(credentials).collect { res ->
                if (res.status != Resource.Status.LOADING && res.data == false) {
                    loginErrorMessage.send(Event(res.message ?: "Erreur"))
                }
                handleLoginResult(res)
            }
        }
    }

    private suspend fun handleLoginResult(res: Resource<Boolean>) {
        showLoading.send(res.status == Resource.Status.LOADING)

        if (res.status != Resource.Status.LOADING && res.data == true) {
            navigateToDashboard.send(Event(Unit))
        }
    }

    /**
     * Displays the information about the universal code or hides the information
     *
     * @param shouldShow True if the information should be shown or false is the information should
     * be hidden
     */
    fun displayUniversalCodeInfo(shouldShow: Boolean) {
        vmScope.launch { displayUniversalCodeDialog.send(shouldShow) }
    }

    fun setUniversalCode(universalCode: String) {
        vmScope.launch {
            val universalCode = UniversalCode(universalCode)

            this@LoginViewModel.universalCode = universalCode

            when (universalCode.error) {
                UniversalCode.Error.EMPTY -> universalCodeErrorMessage.send(Event("error_field_required"))
                UniversalCode.Error.INVALID -> universalCodeErrorMessage.send(Event("string.error_invalid_universal_code"))
                null -> null
            }
        }
    }

    fun setPassword(password: String) {
        vmScope.launch {
            this@LoginViewModel.password = password

            when {
                password.isEmpty() -> passwordErrorMessage.send(Event("error_field_required"))
                else -> null
            }
        }
    }
}