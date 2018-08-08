package ca.etsmtl.etsmobile.presentation.login

import android.app.Activity
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.presentation.MainActivity
import ca.etsmtl.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.etsmobile.util.call
import ca.etsmtl.etsmobile.util.isDeviceConnected
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Etudiant
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
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
    private val activityToGoTo by lazy { MutableLiveData<Class<out Activity>>() }
    private val hideKeyboard by lazy { MutableLiveData<Void>() }

    /**
     * This [LiveData] indicates whether the user credentials are valid or not. It's a
     * [Transformations.switchMap] which is triggered when [setUserCredentials] is called. The new
     * [SignetsUserCredentials] are used to check if an instance of [Etudiant] can be fetched. If
     * an instance can be fetched, it means that the user's credentials are valid. As a result, the
     * new [SignetsUserCredentials] are saved and stored in [LoginRepository].
     */
    private val userCredentialsValid: LiveData<Resource<Boolean>> by lazy {
        Transformations.switchMap(userCredentials) { userCredentials ->
            // Fetch Etudiant instance
            Transformations.map(repository.getInfoEtudiant(userCredentials, app.isDeviceConnected())) { res ->
                transformEtudiantResToBooleanRes(res).apply {
                    if (userCredentialsValid(this)) {
                        loginRepository.saveUserCredentialsIfNeeded(userCredentials)
                        activityToGoTo.value = MainActivity::class.java
                    }
                }
            }
        }
    }

    /**
     * Returns an error message to be displayed to the user.
     *
     * This is triggered after the user's credentials have been submitted.
     *
     * @return A [LiveData] containing an error message to be displayed to the user
     */
    fun getErrorMessage(): LiveData<String> = Transformations.map(userCredentialsValid) {
        when {
            it.status == Resource.ERROR -> it.message
            else -> null
        }
    }

    /**
     * Returns whether or not a loading animation should be displayed.
     *
     * This is triggered after the user's credentials have been submitted.
     *
     * @return A [LiveData] with a [Boolean] which would be set to true a loading animation should
     * be displayed
     */
    fun getShowLoading(): LiveData<Boolean> = Transformations.map(userCredentialsValid) {
        it.status == Resource.SUCCESS || it.status == Resource.LOADING
    }

    /**
     * Returns an error message related to the universal code field.
     *
     * This is triggered after the universal code has been submitted.
     *
     * @return A [LiveData] containing an error message related to the universal code field. The
     * error message is null if there is not error in the universal code.
     */
    fun getUniversalCodeError(): LiveData<String> = Transformations.map(universalCode) {
        when {
            it.isEmpty() -> app.getString(R.string.error_field_required)
            !it.matches(Regex("[a-zA-Z]{2}\\d{5}")) -> app.getString(R.string.error_invalid_universal_code)
            else -> null
        }
    }

    /**
     * Returns an error message related to the password field.
     *
     * This is triggered after the password has been submitted.
     *
     * @return A [LiveData] containing an error message related to the password field. The
     * error message is null if there is not error in the password.
     */
    fun getPasswordError(): LiveData<String> = Transformations.map(password) {
        when {
            it.isEmpty() -> app.getString(R.string.error_field_required)
            else -> null
        }
    }

    /**
     * Returns the activity to navigate to.
     *
     * @return A [LiveData] containing an [Activity] to navigate to.
     */
    fun getActivityToGoTo(): LiveData<Class<out Activity>> = activityToGoTo

    fun getHideKeyboard(): LiveData<Void> = hideKeyboard

    /**
     * Verifies that a given resource is not null and that his status is not [Resource.LOADING]
     *
     * @param blnResource The [Resource] to verify
     * @return true if the resource is not null and that his status is not [Resource.LOADING]
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

    fun setUniversalCode(universalCode: String) {
        this.universalCode.value = universalCode
    }

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
            hideKeyboard.call()
            userCredentials.value = SignetsUserCredentials(universalCode.value!!, password.value!!)
        }
    }

    /**
     * Submits the saved user's credentials [Lifecycle.Event.ON_START]
     *
     * Calls [submitCredentials] with the saved user's credentials if they are not null
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun submitSavedCredentials() {
        loginRepository.getSavedUserCredentials()?.let {
            universalCode.value = it.codeAccesUniversel
            password.value = it.motPasse
            submitCredentials()
        }
    }

    fun clickOnAppletsLogo() {
        activityToGoTo.value = AboutActivity::class.java
    }
}
