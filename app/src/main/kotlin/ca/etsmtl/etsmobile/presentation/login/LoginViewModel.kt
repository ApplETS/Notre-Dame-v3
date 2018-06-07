package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.util.isDeviceConnected
import ca.etsmtl.repos.data.model.Resource
import ca.etsmtl.repos.data.model.signets.Etudiant
import ca.etsmtl.repos.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repos.data.repository.signets.InfoEtudiantRepository
import ca.etsmtl.repos.data.repository.signets.login.LoginRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private val loginRepository: LoginRepository,
    private val app: App
) : AndroidViewModel(app) {

    private val userCredentialsLD: MutableLiveData<SignetsUserCredentials> by lazy {
        MutableLiveData<SignetsUserCredentials>()
    }

    /**
     * This [LiveData] indicates whether the user credentials are valid or not. It's a
     * [Transformations.switchMap] which is triggered when [setUserCredentials] is called. The new
     * [SignetsUserCredentials] are used to check if an instance of [Etudiant] can be fetched. If
     * an instance can be fetched, it means that the user's credentials are valid. As a result, the
     * new [SignetsUserCredentials] are saved and stored in [LoginRepository].
     */
    val userCredentialsValidLD: LiveData<Resource<Boolean>> by lazy {
        Transformations.switchMap(userCredentialsLD) { userCredentials ->
            // Fetch Etudiant instance
            Transformations.map(repository.getInfoEtudiant(userCredentials, app.isDeviceConnected())) { res ->
                val blnRes = transformEtudiantResToBooleanRes(res)

                if (userCredentialsValid(blnRes)) {
                    loginRepository.saveUserCredentialsIfNeeded(userCredentials)
                }

                blnRes
            }
        }
    }

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

    /**
     * Set the universal code
     *
     * @param universalCode the user's universal code
     * @return A [FieldStatus] instance indicating whether the universal code is valid or not
     */
    fun setUniversalCode(universalCode: String): FieldStatus {
        return if (universalCode.isEmpty()) {
            FieldStatus(false, app.getString(R.string.error_field_required))
        } else if (!universalCode.matches(Regex("[a-zA-Z]{2}\\d{5}"))) {
            FieldStatus(false, app.getString(R.string.error_invalid_universal_code))
        } else {
            FieldStatus(true, null)
        }
    }

    /**
     * Set the password
     *
     * @param password the user's password
     * @return A [FieldStatus] instance indicating whether the password is valid or not
     */
    fun setPassword(password: String): FieldStatus {
        return if (password.isEmpty()) {
            FieldStatus(false, app.getString(R.string.error_field_required))
        } else {
            FieldStatus(true, null)
        }
    }

    /**
     * Triggers a verification of the validity of the [userCredentials].
     * The result is posted to the [userCredentialsValidLD]
     *
     * @param userCredentials the credentials of the user
     */
    fun setUserCredentials(userCredentials: SignetsUserCredentials) {
        /** Triggers [userCredentialsValidLD] **/
        userCredentialsLD.value = userCredentials
    }

    fun getSavedUserCredentials(): SignetsUserCredentials? {
        return loginRepository.getSavedUserCredentials()
    }
}
