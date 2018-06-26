package ca.etsmtl.repository.data.repository.signets

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.AppExecutors
import ca.etsmtl.repository.data.api.ApiResponse
import ca.etsmtl.repository.data.api.SignetsApi
import ca.etsmtl.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.repository.data.model.Resource
import ca.etsmtl.repository.data.model.signets.Cours
import ca.etsmtl.repository.data.model.signets.ListeDeCours
import ca.etsmtl.repository.data.model.signets.Session
import ca.etsmtl.repository.data.model.signets.SignetsModel
import ca.etsmtl.repository.data.model.signets.SignetsUserCredentials
import ca.etsmtl.repository.data.repository.NetworkBoundResource
import javax.inject.Inject

/**
 * This repository provides access to the user's courses.
 *
 * Created by Sonphil on 23-06-18.
 */

class CoursRepository @Inject constructor(
    appExecutors: AppExecutors,
    private val api: SignetsApi,
    private val dao: CoursDao
) : SignetsRepository(appExecutors) {

    /**
     * Returns the user's courses
     *
     * @param userCredentials The user's credentials
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getCours(userCredentials: SignetsUserCredentials, shouldFetch: Boolean = true): LiveData<Resource<List<Cours>>> {
        return object : NetworkBoundResource<List<Cours>, SignetsModel<ListeDeCours>>(appExecutors) {
            override fun saveCallResult(item: SignetsModel<ListeDeCours>) {
                item.data?.liste?.let { dao.insertAll(*it.toTypedArray()) }
            }

            override fun shouldFetch(data: List<Cours>?): Boolean {
                return shouldFetch
            }

            override fun loadFromDb(): LiveData<List<Cours>> {
                return dao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeDeCours>>> {
                return transformApiLiveData(api.listeCours(userCredentials))
            }
        }.asLiveData()
    }

    /**
     * Returns the user's courses for the given session
     *
     * @param userCredentials The user's credentials
     * @param session The session
     * @param shouldFetch True if the data should be fetched from the network. False if the the data
     * should only be fetched from the DB.
     */
    fun getCoursSession(
        userCredentials: SignetsUserCredentials,
        session: Session,
        shouldFetch: Boolean = true
    ): LiveData<Resource<List<Cours>>> {
        return object : NetworkBoundResource<List<Cours>, SignetsModel<ListeDeCours>>(appExecutors) {
            override fun saveCallResult(item: SignetsModel<ListeDeCours>) {
                item.data?.liste?.let { dao.insertAll(*it.toTypedArray()) }
            }

            override fun shouldFetch(data: List<Cours>?): Boolean {
                return shouldFetch
            }

            override fun loadFromDb(): LiveData<List<Cours>> {
                return dao.getCoursBySession(session.abrege)
            }

            override fun createCall(): LiveData<ApiResponse<SignetsModel<ListeDeCours>>> {
                return transformApiLiveData(api.listeCoursIntervalleSessions(
                        userCredentials.codeAccesUniversel,
                        userCredentials.motPasse,
                        session.abrege,
                        session.abrege
                ))
            }
        }.asLiveData()
    }
}