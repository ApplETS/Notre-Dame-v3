package ca.etsmtl.etsmobile3.presentation.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import ca.etsmtl.etsmobile3.data.model.Resource
import ca.etsmtl.etsmobile3.data.model.UserCredentials
import ca.etsmtl.etsmobile3.data.repository.InfoEtudiantRepository
import ca.etsmtl.etsmobile3.presentation.App
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
                        val etudiant = res.data?.data

                        if (etudiant?.erreur == null || TextUtils.isEmpty(etudiant.erreur)) {
                            userCredentialsValid.value = Resource.success(true)
                        } else {
                            userCredentialsValid.value = Resource.error(res.message, false)
                        }

                        userCredentialsValid.removeSource(infoEtudiantLD)
                    }
                    Resource.ERROR -> {
                        userCredentialsValid.value = Resource.error(res.message, false)

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
