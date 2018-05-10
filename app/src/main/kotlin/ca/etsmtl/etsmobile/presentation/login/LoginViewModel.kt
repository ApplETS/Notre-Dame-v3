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
            Transformations.switchMap(repository.getInfoEtudiant(userCredentials, NetworkUtils.isDeviceConnected(getApplication()))) { res ->
                val credentialsValidBooleanLiveData: LiveData<Resource<Boolean>> = getUserCredentialsValidBooleanLiveData(res)

                if (userCredentialsValid(credentialsValidBooleanLiveData.value)) {
                    loginRepository.saveUserCredentialsIfNeeded(userCredentials)
                    res.data?.let { logUserFabricCrashlytics(userCredentials, it) }
                }

                credentialsValidBooleanLiveData
            }
        }
    }

    /**
     * Set some user information which will be logged with crashes
     */
    private fun logUserFabricCrashlytics(userCredentials: SignetsUserCredentials, etudiant: Etudiant) {
        if (Fabric.isInitialized()) {
            Crashlytics.setUserIdentifier(userCredentials.codeAccesUniversel)
            Crashlytics.setUserName(etudiant.prenom + " " + etudiant.nom)
        }
    }

    private fun userCredentialsValid(blnResource: Resource<Boolean>?): Boolean {
        if (blnResource != null && blnResource.status == Resource.SUCCESS) {
            return blnResource.data!!
        }

        return false
    }

    /**
     * Returns a [LiveData] containing a [Boolean] Resource which indicates whether the
     * [SignetsUserCredentials] are valid or not.
     */
    private fun getUserCredentialsValidBooleanLiveData(res: Resource<Etudiant>?): MutableLiveData<Resource<Boolean>> {
        val resultLiveData = MutableLiveData<Resource<Boolean>>()

        if (res != null) {
            when (res.status) {
                Resource.SUCCESS -> {
                    resultLiveData.value = Resource.success(true)
                }
                Resource.ERROR -> {
                    val errorStr = res.message ?: getApplication<App>().getString(R.string.error)
                    resultLiveData.value = Resource.error(errorStr, false)
                }
                Resource.LOADING -> {
                    resultLiveData.value = Resource.loading(true)
                }
            }
        } else {
            val errorStr = getApplication<App>().getString(R.string.error)
            resultLiveData.value = Resource.error(errorStr, false)
        }

        return resultLiveData
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
