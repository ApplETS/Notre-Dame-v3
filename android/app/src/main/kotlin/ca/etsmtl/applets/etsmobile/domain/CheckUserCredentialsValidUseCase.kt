package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.extension.isDeviceConnected
import model.Resource
import ca.etsmtl.applets.repository.data.repository.signets.InfoEtudiantRepository
import model.Etudiant
import model.UserCredentials
import javax.inject.Inject

class CheckUserCredentialsValidUseCase @Inject constructor(
    private val repository: InfoEtudiantRepository,
    private val app: App
) {
    operator fun invoke(userCredentials: UserCredentials): LiveData<Resource<Boolean>> {
        val shouldFetch: (data: Etudiant?) -> Boolean = { it == null }

        return Transformations.map(repository.getInfoEtudiant(userCredentials, shouldFetch)) { res ->
            transformEtudiantResToBooleanRes(res)
        }
    }

    /**
     * Transforms a [Resource<Etudiant>] to a [Resource<Boolean>]. The result [Resource] contains
     * an [Boolean] which indicates whether the credentials used to fetch the [Etudiant] of the
     * original [Resource] is valid or not.
     *
     * @param res The Resource<Etudiant>
     * @return The transformed [Resource]
     */
    private fun transformEtudiantResToBooleanRes(res: Resource<Etudiant>?): Resource<Boolean> {
        if (res != null) {
            when (res.status) {
                Resource.Status.SUCCESS -> {
                    return Resource.success(true)
                }
                Resource.Status.ERROR -> {
                    val errorStr = getErrorMessage(res)
                    return Resource.error(errorStr, false)
                }
                Resource.Status.LOADING -> {
                    return Resource.loading(null)
                }
            }
        }

        val errorStr = app.getString(R.string.error)
        return Resource.error(errorStr, false)
    }

    /**
     * Generates an error for the given [Resource<Etudiant]
     *
     * If the device isn't connected, [R.string.error_no_internet_connection] is returned. If the
     * the device is connected, the [Resource<Etudiant]'s error message is returned. However, if its
     * error message is null, [R.string.error] is returned.
     *
     * @return error message
     */
    private fun getErrorMessage(res: Resource<Etudiant>): String {
        return if (app.isDeviceConnected()) {
            res.message ?: app.getString(R.string.error)
        } else {
            app.getString(R.string.error_no_internet_connection)
        }
    }
}