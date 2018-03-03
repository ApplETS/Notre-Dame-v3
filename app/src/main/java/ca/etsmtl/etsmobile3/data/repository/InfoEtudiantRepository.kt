package ca.etsmtl.etsmobile3.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.Etudiant
import ca.etsmtl.etsmobile3.data.model.Resource
import ca.etsmtl.etsmobile3.data.model.UserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 02-03-18.
 */

class InfoEtudiantRepository @Inject constructor(
        private val api: SignetsApi
) {
    fun getInfoEtudiant(userCredentials: UserCredentials): LiveData<Resource<Etudiant>> {

        /*return object : NetworkBoundResource<Etudiant, Etudiant>() {
            val fakeDbLD: MutableLiveData<Etudiant> = MutableLiveData()

            override fun saveCallResult(item: Etudiant) {
                fakeDbLD.postValue(item)
            }


            override fun shouldFetch(data: Etudiant?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Etudiant> {

                return fakeDbLD
            }

            override fun createCall(): LiveData<ApiResponse<Etudiant>> {
                return api.infoEtudiant(userCredentials)
            }

        }.asLiveData()*/

        // TODO: implement the real thing
        val testLD: MediatorLiveData<Resource<Etudiant>> = MediatorLiveData()
        testLD.value = Resource.loading(null)
        testLD.addSource(api.infoEtudiant(userCredentials)) { response ->
            if (response != null) {
                if (response.isSuccessful && response.body != null)
                    testLD.value = Resource.success(response.body)
                else
                    testLD.value = Resource.error(response.errorMessage, response.body)
            }
        }

        return testLD

    }
}
