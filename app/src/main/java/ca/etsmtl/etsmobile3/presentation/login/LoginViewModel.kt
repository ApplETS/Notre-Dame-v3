package ca.etsmtl.etsmobile3.presentation.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ca.etsmtl.etsmobile3.model.Resource
import ca.etsmtl.etsmobile3.model.UserCredentials
import ca.etsmtl.etsmobile3.presentation.App
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * Created by Sonphil on 28-02-18.
 */

class LoginViewModel @Inject constructor(): ViewModel() {

    private val userCreadentialsValid: MutableLiveData<Resource<Boolean>> by lazy {
        MutableLiveData<Resource<Boolean>>()
    }

    fun getUserCredentials(): LiveData<Resource<Boolean>> {
        return userCreadentialsValid
    }

    fun getCachedUserCredentials(): UserCredentials? {
        val userCredentials = UserCredentials("AM41234", "test!")

        App.userCredentials = userCredentials

        return userCredentials
    }

    fun setUserCredentials(userCredentials: UserCredentials): LiveData<Resource<Boolean>> {
        userCreadentialsValid.value = Resource.loading(true)

        App.userCredentials = userCredentials

        // Simulate
        thread {
            Thread.sleep(2000)
            userCreadentialsValid.postValue(Resource.success(true))
        }

        return userCreadentialsValid
    }
}
