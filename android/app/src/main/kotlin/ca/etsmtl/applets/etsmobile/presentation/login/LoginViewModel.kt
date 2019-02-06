package ca.etsmtl.applets.etsmobile.presentation.login

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.CheckUserCredentialsValidUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchSavedSignetsUserCredentialsUserCase
import ca.etsmtl.applets.etsmobile.domain.SaveSignetsUserCredentialsUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.call
import ca.etsmtl.applets.repository.data.model.Resource
import model.SignetsUserCredentials
import model.UniversalCode
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val fetchSavedSignetsUserCredentialsUserCase: FetchSavedSignetsUserCredentialsUserCase,
    private val checkUserCredentialsValidUseCase: CheckUserCredentialsValidUseCase,
    private val saveSignetsUserCredentialsUseCase: SaveSignetsUserCredentialsUseCase,
    private val app: App
) : AndroidViewModel(app), LifecycleObserver {
    private val universalCode: MutableLiveData<UniversalCode> = MutableLiveData()
    private val password: MutableLiveData<String> = MutableLiveData()
    private val userCredentials: MutableLiveData<SignetsUserCredentials> = MutableLiveData()
    private val _navigateToDashboard = MutableLiveData<Event<Unit>>()
    val navigateToDashboard: LiveData<Event<Unit>> = _navigateToDashboard
    /**
     * This [LiveData] indicates whether the user credentials are valid or not. It's a
     * [Transformations.switchMap] which is triggered when [userCredentials] is called. If the
     * response doesn't contain an error, the [SignetsUserCredentials] are considered valid and are
     * saved. Moreover, a navigation to the dashboard will be triggered.
     */
    private val userCredentialsValid: LiveData<Resource<Boolean>> = Transformations.switchMap(userCredentials) { userCredentials ->
        Transformations.map(checkUserCredentialsValidUseCase(userCredentials)) {
            if (it.status != Resource.Status.LOADING && it.data == true) {
                saveSignetsUserCredentialsUseCase(userCredentials)
                _navigateToDashboard.value = Event(Unit)
            }

            it
        }
    }
    private val _navigateToLogin = MediatorLiveData<Void>().apply {
        addSource(userCredentialsValid) {
            it?.let {
                if (it.status != Resource.Status.LOADING && it.data == false) {
                    this.call()
                }
            }
        }
    }
    /** An error message to be displayed to the user **/
    val errorMessage: LiveData<Event<String>> = MediatorLiveData<Event<String>>().apply {
        this.addSource(userCredentialsValid) {
            if (it != null && it.status == Resource.Status.ERROR) {
                this.value = Event(it.message ?: app.getString(R.string.error))
            }
        }
    }

    private val displayUniversalCodeDialogMediator: MediatorLiveData<Boolean> = MediatorLiveData()

    /**
     * A [LiveData] which is called when the [LoginFragment] needs to be displayed
     *
     * The [LoginFragment] needs to be displayed when the user needs to login for the first time or
     * if his credentials are no longer valid.
     */
    val navigateToLogin: LiveData<Void> = _navigateToLogin

    /**
     * A [LiveData] with a [Boolean] which would be set to true a loading animation should
     * be displayed
     *
     * This is triggered after the user's credentials have been submitted.
     */
    val showLoading: LiveData<Boolean> = Transformations.map(userCredentialsValid) {
        it.status == Resource.Status.SUCCESS || it.status == Resource.Status.LOADING
    }

    /**
     * A [LiveData] containing an error message related to the universal code field. The
     * error message is null if there is not error in the universal code.
     *
     * This is triggered after the universal code has been submitted.
     */
    val universalCodeError: LiveData<String> = Transformations.map(universalCode) {
        when (it.error) {
            UniversalCode.Error.EMPTY -> app.getString(R.string.error_field_required)
            UniversalCode.Error.INVALID -> app.getString(R.string.error_invalid_universal_code)
            null -> null
        }
    }

    /**
     * A [LiveData] containing an error message related to the password field. The
     * error message is null if there is not error in the password.
     *
     * This is triggered after the password has been submitted.
     */
    val passwordError: LiveData<String> = Transformations.map(password) {
        when {
            it.isEmpty() -> app.getString(R.string.error_field_required)
            else -> null
        }
    }

    /**
     * A [LiveData] which is called when the keyboard needs to be hidden
     *
     * The keyboard needs to be hidden when the user's credentials are submitted.
     */
    val hideKeyboard: LiveData<Void> = Transformations.map(userCredentials) { null }

    /**
     * A [LiveData] indicating whether the universal code info dialog should be displayed or
     * not
     */
    val displayUniversalCodeDialog: LiveData<Boolean> = displayUniversalCodeDialogMediator

    /**
     * Set the user's universal code
     *
     * This will trigger a validity check for the given universal code.
     *
     * @param universalCode
     */
    fun setUniversalCode(universalCode: UniversalCode) {
        this.universalCode.value = universalCode
    }

    /**
     * Set the user's password
     *
     * This will trigger a validity check for the given password.
     *
     * @param password
     */
    fun setPassword(password: String) {
        this.password.value = password
    }

    /**
     * Submits the user's credentials
     *
     * If the universal code and the password are not null, this will trigger a validity check of
     * the user's credentials.
     */
    fun submitCredentials() {
        if (universalCode.value != null && password.value != null) {
            userCredentials.value = SignetsUserCredentials(universalCode.value!!, password.value!!)
        }
    }

    /**
     * Submits the saved user's credentials on [Lifecycle.Event.ON_START]
     *
     * Calls [submitCredentials] with the saved user's credentials if they are not null
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun submitSavedCredentials() {
        with(fetchSavedSignetsUserCredentialsUserCase()) {
            if (this == null) {
                _navigateToLogin.call()
            } else {
                universalCode.value = this.codeAccesUniversel
                password.value = this.motPasse
                submitCredentials()
            }
        }
    }

    /**
     * Displays the information about the universal code or hides the information
     *
     * @param shouldShow True if the information should be shown or false is the information should
     * be hidden
     */
    fun displayUniversalCodeInfo(shouldShow: Boolean) {
        displayUniversalCodeDialogMediator.value = shouldShow
    }
}
