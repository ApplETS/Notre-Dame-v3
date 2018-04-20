package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.SharedPreferences
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.data.repository.LogOutRepository
import ca.etsmtl.etsmobile.presentation.App
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private val prefs: SharedPreferences,
    private val logOutRepository: LogOutRepository,
    app: App
) : AndroidViewModel(app) {

    companion object {
        private const val UNIVERSAL_CODE_PREF = "UniversalCodePref"
    }

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
     * the case, the new [UserCredentials] are saved and stored in [App].
     */
    private val userCredentialsValidLD: LiveData<Resource<Boolean>> by lazy {
        Transformations.switchMap(userCredentialsLD) { userCredentials ->
            Transformations.switchMap(repository.getInfoEtudiant(userCredentials, true)) { res ->
                val booleanLiveData = getBooleanLiveData(res)

                val blnResource = booleanLiveData.value
                if (blnResource != null && blnResource.status == Resource.SUCCESS && blnResource.data!!) {
                    App.userCredentials.set(userCredentials)

                    saveUserCredentials(userCredentials)
                }

                booleanLiveData
            }
        }
    }

    /**
     * Returns a [LiveData] with a [Boolean] which indicates whether the [UserCredentials] are
     * valid or not.
     */
    private fun getBooleanLiveData(res: Resource<Etudiant>?): MutableLiveData<Resource<Boolean>> {
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
        val codeAccesUniversel = getSavedUniversalCode()

        var userCredentials: UserCredentials? = null

        if (codeAccesUniversel != null) {
            val motPasse = getSavedPassword()

            if (motPasse != null) {
                userCredentials = UserCredentials(codeAccesUniversel, motPasse)
                App.userCredentials.set(userCredentials)
            }
        }

        return userCredentials
    }

    private fun saveUserCredentials(userCredentials: UserCredentials) {
        saveUniversalCode(userCredentials.codeAccesUniversel)
        savePassword(userCredentials.motPasse)
    }

    private fun saveUniversalCode(universalCode: String) {
        with(prefs.edit()) {
            putString(UNIVERSAL_CODE_PREF, universalCode)
            apply()
        }
    }

    private fun getSavedUniversalCode(): String? {
        return prefs.getString(UNIVERSAL_CODE_PREF, null)
    }

    private fun getSavedPassword(): String? {
        // TODO: Get password
        return ""
    }

    private fun savePassword(password: String) {
        // TODO: Save password in Android Keystore System https://developer.android.com/training/articles/keystore.html
    }

    fun logOut(): LiveData<Boolean> {
        App.userCredentials.set(null)

        prefs.edit().clear().apply()

        return logOutRepository.clearDb()
    }
}
