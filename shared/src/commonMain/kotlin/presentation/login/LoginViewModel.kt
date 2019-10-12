package presentation.login

import di.Inject
import domain.CheckUserCredentialsAreValidUseCase
import domain.LoginWithSavedCredentialsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Resource
import model.UniversalCode
import presentation.ViewModel
import utils.LastValueReceiveChannel
import utils.localizable.LocalizedString

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val checkUserCredentialsAreValidUseCase: CheckUserCredentialsAreValidUseCase,
    private val loginWithSavedCredentialsUseCase: LoginWithSavedCredentialsUseCase
) : ViewModel() {
    var universalCode: UniversalCode = UniversalCode("")
        private set
    private var password: String = ""
    /** Emits when a navigation to the dashboard should be triggered **/
    val navigateToDashboard = Channel<Unit>()
    val displayUniversalCodeDialog = LastValueReceiveChannel<Boolean>()
    val showLoading = Channel<Boolean>()
    val universalCodeErrorMessage = LastValueReceiveChannel<LocalizedString?>()
    val passwordErrorMessage = LastValueReceiveChannel<LocalizedString?>()
    val loginErrorMessage = Channel<String>()
    val hideKeyboard = Channel<Unit>()

    fun submitSavedCredentials() {
        vmScope.launch {
            loginWithSavedCredentialsUseCase().collect { res ->
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
            checkUserCredentialsAreValidUseCase(universalCode, password).collect { res ->
                if (res.status != Resource.Status.LOADING && res.data == false) {
                    loginErrorMessage.send(res.message ?: LocalizedString.GENERIC_ERROR.value)
                }
                handleLoginResult(res)
            }
        }
    }

    private suspend fun handleLoginResult(res: Resource<Boolean>) {
        showLoading.send(res.status == Resource.Status.LOADING)

        if (res.status != Resource.Status.LOADING && res.data == true) {
            navigateToDashboard.send(Unit)
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