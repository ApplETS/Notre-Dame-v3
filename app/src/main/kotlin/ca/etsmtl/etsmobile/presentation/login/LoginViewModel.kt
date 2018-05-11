package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.data.repository.signets.InfoEtudiantRepository
import ca.etsmtl.etsmobile.data.repository.signets.login.LoginRepository
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.util.NetworkUtils
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
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

    private var universalCode: String? = null
    private var password: String? = null

    /**
     * This [LiveData] indicates whether the user credentials are valid or not. It's a
     * [Transformations.switchMap] which is triggered when [setUserCredentials] is called. The new
     * [SignetsUserCredentials] are used to check if an instance of [Etudiant] can be fetched. If
     * that is the case, is means that the user's credentials are valid. The new
     * [SignetsUserCredentials] are saved and stored in [LoginRepository].
     */
    val userCredentialsValidLD: LiveData<Resource<Boolean>> by lazy {
        Transformations.switchMap(userCredentialsLD) { userCredentials ->
            // Fetch Etudiant instance
            Transformations.switchMap(repository.getInfoEtudiant(userCredentials, NetworkUtils.isDeviceConnected(getApplication()))) { res ->
                val blnRes = transformEtudiantResToBooleanRes(res)

                if (userCredentialsValid(blnRes)) {
                    res?.data?.let { logUserToFabricCrashlytics(userCredentials, it) }

                    loginRepository.saveUserCredentialsIfNeeded(userCredentials)
                }

                val credentialsValidBooleanLiveData = MutableLiveData<Resource<Boolean>>()
                credentialsValidBooleanLiveData.value = blnRes

                credentialsValidBooleanLiveData
            }
        }
    }

    /**
     * Set some user information which will be logged with crashes
     */
    private fun logUserToFabricCrashlytics(userCredentials: SignetsUserCredentials, etudiant: Etudiant) {
        if (Fabric.isInitialized()) {
            Crashlytics.setUserIdentifier(userCredentials.codeAccesUniversel)
            Crashlytics.setUserName(etudiant.prenom + " " + etudiant.nom)
        }
    }

    /**
     * Verify that the resource is not null and that his status is not [Resource.LOADING]
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

    private fun transformEtudiantResToBooleanRes(res: Resource<Etudiant>?): Resource<Boolean> {
        if (res != null) {
            when (res.status) {
                Resource.SUCCESS -> {
                    return Resource.success(true)
                }
                Resource.ERROR -> {
                    val errorStr = res.message ?: getApplication<App>().getString(R.string.error)
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
     * Set the universal code
     *
     * @param universalCode the user's universal code
     * @return A [FieldStatus] instance indicating whether the password is valid or not
     */
    fun setUniversalCode(universalCode: String): FieldStatus {
        this.universalCode = universalCode

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
        this.password = password

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
