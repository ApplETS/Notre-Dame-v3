package presentation.login

import di.Inject
import domain.CheckUserCredentialsAreValidUseCase
import domain.LoginWithSavedCredentials
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Resource
import model.SignetsUserCredentials
import model.UniversalCode
import presentation.Event
import presentation.ViewModel
import utils.LastValueReceiveChannel
import utils.localizable.LocalizedString

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val checkUserCredentialsAreValidUseCase: CheckUserCredentialsAreValidUseCase,
    private val loginWithSavedCredentials: LoginWithSavedCredentials
) : ViewModel() {
    private var universalCode: UniversalCode = UniversalCode("")
    private var password: String = ""
    val navigateToDashboard = Channel<Event<Unit>>()
    val displayUniversalCodeDialog = LastValueReceiveChannel<Boolean>()
    val showLoading = Channel<Boolean>()
    val universalCodeErrorMessage = LastValueReceiveChannel<LocalizedString?>()
    val passwordErrorMessage = LastValueReceiveChannel<LocalizedString?>()
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
                    loginErrorMessage.send(Event(res.message ?: LocalizedString.GENERIC_ERROR.value))
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
        vmScope.launch { displayUniversalCodeDialog.submit(shouldShow) }
    }

    fun setUniversalCode(universalCode: String) {
        vmScope.launch {
            val universalCode = UniversalCode(universalCode)

            this@LoginViewModel.universalCode = universalCode

            universalCodeErrorMessage.submit(
                when (universalCode.error) {
                    UniversalCode.Error.EMPTY -> LocalizedString.FIELD_REQUIRED
                    UniversalCode.Error.INVALID -> LocalizedString.INVALID_UNIVERSAL_CODE
                    else -> null
                }
            )
        }
    }

    fun setPassword(password: String) {
        vmScope.launch {
            this@LoginViewModel.password = password

            when {
                password.isEmpty() -> passwordErrorMessage.submit(LocalizedString.FIELD_REQUIRED)
                else -> passwordErrorMessage.submit(null)
            }
        }
    }
}