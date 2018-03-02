package ca.etsmtl.etsmobile3.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues.TAG
import android.util.Log
import ca.etsmtl.etsmobile3.data.api.ApiResponse
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.Data
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
    fun getInfoEtudiant(userCredentials: UserCredentials): LiveData<Resource<Data<Etudiant>>> {

        return object : NetworkBoundResource<Data<Etudiant>, Data<Etudiant>>() {
            override fun saveCallResult(item: Data<Etudiant>) {
                Log.d(TAG, "test")
                //TODO:
            }


            override fun shouldFetch(data: Data<Etudiant>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Data<Etudiant>> {
                val testLD: MutableLiveData<Data<Etudiant>> = MutableLiveData()
                val d = Data(Etudiant())
                d.data?.nom = "tests"
                d.data?.prenom = "test"
                testLD.postValue(d)

                return testLD
            }

            override fun createCall(): LiveData<ApiResponse<Data<Etudiant>>> {
                return api.infoEtudiant(userCredentials)
            }

        }.asLiveData()
    }
}
