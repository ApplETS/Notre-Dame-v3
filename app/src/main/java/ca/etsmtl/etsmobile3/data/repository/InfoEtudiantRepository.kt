package ca.etsmtl.etsmobile3.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.VisibleForTesting
import android.text.TextUtils
import ca.etsmtl.etsmobile3.data.api.ApiResponse
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.*
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
            val errorStr = getError(response)

            if (TextUtils.isEmpty(errorStr)) // If there was no error ...
                testLD.value = Resource.success(response?.body!!)
            else
                testLD.value = Resource.error(errorStr, response?.body)
        }

        return testLD
    }

    @VisibleForTesting
    fun getError(apiResponse: ApiResponse<out SignetsModel>?): String? {
        if (apiResponse == null)
            return "No Response"

        val error = !apiResponse.isSuccessful || apiResponse.body == null

        return when (error) {
            true -> apiResponse.errorMessage
            false -> getErrorInsideData(apiResponse.body)
        }
    }

    private fun getErrorInsideData(signetsModel: SignetsModel?): String? {
        return when (signetsModel?.getSignetsData() == null) {
            true -> "No Data"
            false -> signetsModel!!.getSignetsData()!!.erreur
        }
    }
}
