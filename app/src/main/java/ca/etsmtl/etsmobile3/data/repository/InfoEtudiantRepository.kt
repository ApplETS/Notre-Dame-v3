package ca.etsmtl.etsmobile3.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.VisibleForTesting
import android.text.TextUtils
import ca.etsmtl.etsmobile3.data.api.ApiResponse
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.Etudiant
import ca.etsmtl.etsmobile3.data.model.Resource
import ca.etsmtl.etsmobile3.data.model.SignetsModel
import ca.etsmtl.etsmobile3.data.model.UserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 02-03-18.
 */

class InfoEtudiantRepository @Inject constructor(
        private val api: SignetsApi
) {
    fun getInfoEtudiant(userCredentials: UserCredentials): LiveData<Resource<Etudiant>> {

        /*return object : NetworkBoundResource<EtudiantWrapper, EtudiantWrapper>() {
            val fakeDbLD: MutableLiveData<EtudiantWrapper> = MutableLiveData()

            override fun saveCallResult(item: EtudiantWrapper) {
                fakeDbLD.postValue(item)
            }


            override fun shouldFetch(data: EtudiantWrapper?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<EtudiantWrapper> {

                return fakeDbLD
            }

            override fun createCall(): LiveData<ApiResponse<EtudiantWrapper>> {
                return api.infoEtudiant(userCredentials)
            }

        }.asLiveData()*/

        // TODO: implement the real thing
        val testLD: MediatorLiveData<Resource<Etudiant>> = MediatorLiveData()
        testLD.value = Resource.loading(null)
        testLD.addSource(api.infoEtudiant(userCredentials)) { response ->
            val errorStr = getError(response)

            if (TextUtils.isEmpty(errorStr)) // If there was no error ...
                testLD.value = Resource.success(response?.body?.data!!)
            else
                testLD.value = Resource.error(errorStr, response?.body?.data)
        }

        return testLD
    }

    @VisibleForTesting
    fun getError(apiResponse: ApiResponse<out SignetsModel<Etudiant>>?): String? {
        if (apiResponse == null)
            return "No Response"

        val error = !apiResponse.isSuccessful || apiResponse.body == null

        return when (error) {
            true -> apiResponse.errorMessage
            false -> getErrorInsideData(apiResponse.body)
        }
    }

    private fun getErrorInsideData(signetsModel: SignetsModel<Etudiant>?): String? {
        return when (signetsModel?.data == null) {
            true -> "No Data"
            false -> signetsModel!!.data!!.erreur
        }
    }
}
