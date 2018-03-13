package ca.etsmtl.etsmobile.data.api

import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.SignetsModel
import ca.etsmtl.etsmobile.data.model.UserCredentials
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton


/**
 * Created by Sonphil on 02-03-18.
 */
@Singleton
interface SignetsApi {
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("infoEtudiant")
    fun infoEtudiant(@Body body: UserCredentials): LiveData<ApiResponse<SignetsModel<Etudiant>>>
}