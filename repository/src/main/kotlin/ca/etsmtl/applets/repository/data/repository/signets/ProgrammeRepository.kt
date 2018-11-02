package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.repository.AppExecutors
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.response.mapper.toProgrammesEntities
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeProgrammes
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.db.dao.signets.ProgrammeDao
import ca.etsmtl.applets.repository.data.db.entity.mapper.toProgrammes
import ca.etsmtl.applets.repository.data.model.Programme
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by Sonphil on 30-10-18.
 */

class ProgrammeRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: ProgrammeDao
) : SignetsRepository(appExecutors) {
    /**
     * Returns a list of the student's programs of study
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch This function is called to determine whether the data should
     * be fetched from the network or only from the DB
     */
    fun getProgrammes(
        userCredentials: SignetsUserCredentials,
        shouldFetch: (data: List<Programme>?) -> Boolean
    ) = object : SignetsNetworkBoundResource<List<Programme>, ApiListeProgrammes>(appExecutors) {
        override fun saveSignetsData(item: ApiListeProgrammes) {
            dao.deleteAll()
            dao.insertAll(item.toProgrammesEntities())
        }

        override fun shouldFetch(data: List<Programme>?) = shouldFetch(data)

        override fun loadFromDb(): LiveData<List<Programme>> {
            return Transformations.map(dao.getAll()) { it.toProgrammes() }
        }

        override fun createCall(): LiveData<ApiResponse<ApiSignetsModel<ApiListeProgrammes>>> {
            return api.listeProgrammes(EtudiantRequestBody(userCredentials))
        }
    }.asLiveData()
}