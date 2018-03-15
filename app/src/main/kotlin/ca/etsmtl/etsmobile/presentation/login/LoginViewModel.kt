package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.presentation.App
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
        private val repository: InfoEtudiantRepository,
        app: App
) : AndroidViewModel(app) {

    companion object {
        private const val UNIVERSAL_CODE_PREF = "UniversalCodePref"
    }

    private val userCredentialsValid: MediatorLiveData<Resource<Boolean>> by lazy {
        MediatorLiveData<Resource<Boolean>>()
    }

    fun getUserCredentials(): LiveData<Resource<Boolean>> {
        return userCredentialsValid
    }

    fun getSavedUserCredentials(): UserCredentials? {
        val codeAccesUniversel = getSavedUniversalCode()

        var userCredentials: UserCredentials? = null

        if (codeAccesUniversel != null) {
            val motPasse = getSavedPassword()

            if (motPasse != null) {
                userCredentials = UserCredentials(codeAccesUniversel, motPasse)
                App.userCredentials = userCredentials
            }
        }

        return userCredentials
    }

    fun setUserCredentials(userCredentials: UserCredentials): LiveData<Resource<Boolean>> {
        userCredentialsValid.value = Resource.loading(true)

        App.userCredentials = userCredentials

        val infoEtudiantLD = repository.getInfoEtudiant(userCredentials, true)

        userCredentialsValid.addSource(infoEtudiantLD) { res ->
            if (res != null) {
                when (res.status) {
                    Resource.SUCCESS -> {
                        userCredentialsValid.value = Resource.success(true)

                        saveUserCredentials(userCredentials)

                        userCredentialsValid.removeSource(infoEtudiantLD)
                    }
                    Resource.ERROR -> {
                        val errorStr = res.message ?: "Error"
                        userCredentialsValid.value = Resource.error(errorStr, false)

                        userCredentialsValid.removeSource(infoEtudiantLD)
                    }
                    Resource.LOADING -> {
                        userCredentialsValid.value = Resource.loading(true)
                    }
                }
            }
        }

        return userCredentialsValid
    }

    private fun saveUserCredentials(userCredentials: UserCredentials) {
        saveUniversalCode(userCredentials.codeAccesUniversel)
        savePassword(userCredentials.motPasse)
    }

    private fun saveUniversalCode(universalCode: String) {
        val app = getApplication<App>()
        val sharedPref = app.getSharedPreferences(app.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(UNIVERSAL_CODE_PREF, universalCode)
            apply()
        }
    }

    private fun getSavedUniversalCode(): String? {
        val app = getApplication<App>()
        val sharedPref = getApplication<App>()
                .getSharedPreferences(app.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE) ?: return null

        return sharedPref.getString(UNIVERSAL_CODE_PREF, null)
    }

    private fun getSavedPassword(): String? {
        // TODO: Get password
        return "TODO"
    }

    private fun savePassword(password: String) {
        // TODO: Save password in Android Keystore System https://developer.android.com/training/articles/keystore.html
    }
}
