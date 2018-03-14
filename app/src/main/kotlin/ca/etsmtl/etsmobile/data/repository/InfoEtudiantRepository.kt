package ca.etsmtl.etsmobile.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.VisibleForTesting
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.EtudiantDao
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.Resource
import ca.etsmtl.etsmobile.data.model.SignetsModel
import ca.etsmtl.etsmobile.data.model.UserCredentials
import javax.inject.Inject


/**
 * Created by Sonphil on 02-03-18.
 */

class InfoEtudiantRepository @Inject constructor(
        private val api: SignetsApi,
        private val dao: EtudiantDao
) {
    fun getInfoEtudiant(userCredentials: UserCredentials): LiveData<Resource<Etudiant>> {

        return object: NetworkBoundResource<Etudiant, SignetsModel<Etudiant>>() {
            override fun saveCallResult(item: SignetsModel<Etudiant>) {
                dao.insertEtudiant(item.data)
            }

            override fun shouldFetch(data: Etudiant?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Etudiant> {
                return dao.getEtudiant()
            }

            override fun createCall(): LiveData<ApiResponse<SignetsModel<Etudiant>>> {
                val resultLiveData = MediatorLiveData<ApiResponse<SignetsModel<Etudiant>>>()
                val apiResponseLiveData = api.infoEtudiant(userCredentials)

                resultLiveData.addSource(apiResponseLiveData) { apiResponse ->
                    val errorStr = getError(apiResponse)

                    if (errorStr.isNullOrEmpty()) {
                        resultLiveData.value = apiResponse
                        resultLiveData.removeSource(apiResponseLiveData)
                    } else {
                        resultLiveData.value = ApiResponse(Throwable(errorStr))
                        resultLiveData.removeSource(apiResponseLiveData)
                    }
                }

                return resultLiveData
            }

        }.asLiveData()
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
