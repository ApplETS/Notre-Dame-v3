package ca.etsmtl.applets.etsmobile.presentation.login

import android.app.Activity
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.call
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
import ca.etsmtl.repository.data.model.Etudiant
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.signets.InfoEtudiantRepository
import ca.etsmtl.repository.data.repository.signets.login.LoginRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private val loginRepository: LoginRepository,
    private val app: App
) : AndroidViewModel(app), LifecycleObserver {

    private val universalCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val password: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val userCredentials: MutableLiveData<SignetsUserCredentials> by lazy {
        MutableLiveData<SignetsUserCredentials>()
    }
    private val _activityToGoTo by lazy { MutableLiveData<Class<out Activity>>() }
    private val showLoginFragmentMediator by lazy {
        MediatorLiveData<Void>().apply {
            addSource(userCredentialsValid) {
                it?.let {
                    if (it.status != Resource.LOADING && it.data == false) {
                        this.call()
                    }
                }
            }
        }
    }
    /** An error message to be displayed to the user **/
    val errorMessage: LiveData<Event<String>> by lazy {
        MediatorLiveData<Event<String>>().apply {
            this.addSource(userCredentialsValid) {
                if (it != null && it.status == Resource.ERROR) {
                    this.value = Event(it.message ?: app.getString(R.string.error))
                }
            }
        }
    }

    private val displayUniversalCodeDialogMediator: MediatorLiveData<Boolean> by lazy {
        MediatorLiveData<Boolean>()
    }

    /**
     * This [LiveData] indicates whether the user credentials are valid or not. It's a
     * [Transformations.switchMap] which is triggered when [userCredentials] is called. The new
     * [SignetsUserCredentials] are used to check if an instance of [Etudiant] can be fetched. The
     * instance is fetched only from the DB. However, if it doesn't exists in the DB, the instance
     * if fetched from the network. If the response doesn't contain an error, the
     * [SignetsUserCredentials] are considered valid and are saved and stored in [LoginRepository].
     * Moreover, a navigation to [MainActivity] will be triggered.
     */
    private val userCredentialsValid: LiveData<Resource<Boolean>> by lazy {
        Transformations.switchMap(userCredentials) { userCredentials ->
            // Fetch [Etudiant] instance
            val shouldFetch: (data: Etudiant?) -> Boolean = { it == null }
            Transformations.map(repository.getInfoEtudiant(userCredentials, shouldFetch)) { res ->
                transformEtudiantResToBooleanRes(res).apply {
                    if (userCredentialsValid(this)) {
                        loginRepository.saveUserCredentialsIfNeeded(userCredentials)
                        _activityToGoTo.value = MainActivity::class.java
                    }
                }
            }
        }
    }

    /**
     * A [LiveData] which is called when the [LoginFragment] needs to be displayed
     *
     * The [LoginFragment] needs to be displayed when the user needs to login for the first time or
     * if his credentials are no longer valid.
     */
    val showLoginFragment: LiveData<Void> = showLoginFragmentMediator

    /**
     * A [LiveData] with a [Boolean] which would be set to true a loading animation should
     * be displayed
     *
     * This is triggered after the user's credentials have been submitted.
     */
    val showLoading: LiveData<Boolean> = Transformations.map(userCredentialsValid) {
        it.status == Resource.SUCCESS || it.status == Resource.LOADING
    }

    /**
     * A [LiveData] containing an error message related to the universal code field. The
     * error message is null if there is not error in the universal code.
     *
     * This is triggered after the universal code has been submitted.
     */
    val universalCodeError: LiveData<String> = Transformations.map(universalCode) {
        when {
            it.isEmpty() -> app.getString(R.string.error_field_required)
            !it.matches(Regex("[a-zA-Z]{2}\\d{5}")) -> app.getString(R.string.error_invalid_universal_code)
            else -> null
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
     * A [LiveData] containing the class of an [Activity] to navigate to.
     */
    val activityToGoTo: LiveData<Class<out Activity>> = _activityToGoTo

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
     * Verifies that a given resource is not null and that his status is [Resource.SUCCESS]
     *
     * @param blnResource The [Resource] to verify
     * @return true if the resource is not null and that his status is [Resource.SUCCESS]
     */
    private fun userCredentialsValid(blnResource: Resource<Boolean>?): Boolean {
        if (blnResource != null && blnResource.status == Resource.SUCCESS) {
            return blnResource.data!!
        }

        return false
    }

    /**
     * Transforms a [Resource<Etudiant>] to a [Resource<Boolean>]. The result [Resource] contains
     * an [Boolean] which indicates whether the credentials used to fetch the [Etudiant] of the
     * original [Resource] is valid or not.
     *
     * @param res The Resource<Etudiant>
     * @return The transformed [Resource]
     */
    private fun transformEtudiantResToBooleanRes(res: Resource<Etudiant>?): Resource<Boolean> {
        if (res != null) {
            when (res.status) {
                Resource.SUCCESS -> {
                    return Resource.success(true)
                }
                Resource.ERROR -> {
                    val errorStr = getErrorMessage(res)
                    return Resource.error(errorStr, false)
                }
                Resource.LOADING -> {
                    return Resource.loading(null)
                }
            }
        }

        val errorStr = getApplication<App>().getString(R.string.error)
        return Resource.error(errorStr, false)
    }

    /**
     * Generates an error for the given [Resource<Etudiant]
     *
     * If the device isn't connected, [R.string.error_no_internet_connection] is returned. If the
     * the device is connected, the [Resource<Etudiant]'s error message is returned. However, if its
     * error message is null, [R.string.error] is returned.
     *
     * @return error message
     */
    private fun getErrorMessage(res: Resource<Etudiant>): String {
        return if (app.isDeviceConnected()) {
            res.message ?: getApplication<App>().getString(R.string.error)
        } else {
            app.getString(R.string.error_no_internet_connection)
        }
    }

    /**
     * Set the user's universal code
     *
     * This will trigger a validity check for the given universal code.
     *
     * @param universalCode
     */
    fun setUniversalCode(universalCode: String) {
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
        with(loginRepository.getSavedUserCredentials()) {
            if (this == null) {
                showLoginFragmentMediator.call()
            } else {
                universalCode.value = this.codeAccesUniversel
                password.value = this.motPasse
                submitCredentials()
            }
        }
    }

    /**
     * Triggers a navigation to [AboutActivity]
     */
    fun clickOnAppletsLogo() {
        _activityToGoTo.value = AboutActivity::class.java
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
