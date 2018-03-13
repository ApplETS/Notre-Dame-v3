package ca.etsmtl.etsmobile.presentation.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.UserCredentials
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile.presentation.App
import javax.inject.Inject

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(
        private val repository: InfoEtudiantRepository
): ViewModel() {

    private val userCredentialsValid: MediatorLiveData<Resource<Boolean>> by lazy {
        MediatorLiveData<Resource<Boolean>>()
    }

    fun getUserCredentials(): LiveData<Resource<Boolean>> {
        return userCredentialsValid
    }

    fun getCachedUserCredentials(): UserCredentials? {
        //TODO: Get cached user credentials
        val userCredentials = UserCredentials("AM41123", "test!")

        App.userCredentials = userCredentials

        return userCredentials
    }

    fun setUserCredentials(userCredentials: UserCredentials): LiveData<Resource<Boolean>> {
        userCredentialsValid.value = Resource.loading(true)

        App.userCredentials = userCredentials

        val infoEtudiantLD = repository.getInfoEtudiant(userCredentials)

        userCredentialsValid.addSource(infoEtudiantLD) { res ->
            if (res != null) {
                when(res.status) {
                    Resource.SUCCESS -> {
                        userCredentialsValid.value = Resource.success(true)

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
}
