package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.data.repository.usercredentials.UserCredentialsRepository
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.util.NetworkUtils
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private val userCredentialsRepository: UserCredentialsRepository,
    app: App
) : AndroidViewModel(app) {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private var userCredentialsAlreadySaved = false

    /**
     * This [LiveData] contains the [UserCredentials] set by the user in the UI. A change triggers
     * [userCredentialsValidLD], [LiveData] created by [Transformations.switchMap].
     */
    private val userCredentialsLD: MutableLiveData<UserCredentials> by lazy {
        MutableLiveData<UserCredentials>()
    }

    /**
     * This [LiveData] indicates whether the user credentials are valid or not. It's a
     * [Transformations.switchMap] which is triggered by a change on [userCredentialsLD]. The new
     * [UserCredentials] are used to check if an instance of [Etudiant] can be fetched. If that is
     * the case, the new [UserCredentials] are saved and stored in [UserCredentialsRepository].
     */
    private val userCredentialsValidLD: LiveData<Resource<Boolean>> by lazy {
        Transformations.switchMap(userCredentialsLD) { userCredentials ->
            Transformations.switchMap(repository.getInfoEtudiant(userCredentials, NetworkUtils.isDeviceConnected(getApplication()))) { res ->
                var credentialsValidBooleanLiveData: LiveData<Resource<Boolean>> = getUserCredentialsValidBooleanLiveData(res)

                if (userCredentialsValid(credentialsValidBooleanLiveData.value)) {
                    UserCredentialsRepository.userCredentials.set(userCredentials)

                    if (!userCredentialsAlreadySaved)
                        userCredentialsRepository.saveUserCredentials(userCredentials)
                }

                credentialsValidBooleanLiveData
            }
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
     * [UserCredentials] are valid or not.
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
     * Returns a [LiveData] indicating whether the user credentials are valid or not
     */
    fun getUserCredentialsIsValid(): LiveData<Resource<Boolean>> {
        return userCredentialsValidLD
    }

    /**
     * Triggers a verification of the validity of the [userCredentials].
     * The result is posted to the [LiveData] returned by [getUserCredentialsIsValid]
     *
     * @param userCredentials the credentials of the user
     */
    fun setUserCredentials(userCredentials: UserCredentials) {
        /*
        Triggers [userCredentialsValid]
         */
        this.userCredentialsLD.value = userCredentials
    }

    fun getSavedUserCredentials(): UserCredentials? {
        val codeAccesUniversel = userCredentialsRepository.getSavedUniversalCode()

        var userCredentials: UserCredentials? = null

        if (codeAccesUniversel != null) {
            val motPasse = userCredentialsRepository.getSavedPassword()

            if (motPasse != null) {
                userCredentials = UserCredentials(codeAccesUniversel, motPasse)
                userCredentialsAlreadySaved = true
                Log.d(TAG, "Successfully retrieved user credentials")
                UserCredentialsRepository.userCredentials.set(userCredentials)
            }
        }

        return userCredentials
    }

    /**
     * Should be called when the user want to log out
     * Clears the user's data
     */
    fun logOut(): LiveData<Boolean> = userCredentialsRepository.clearUserData()
}
