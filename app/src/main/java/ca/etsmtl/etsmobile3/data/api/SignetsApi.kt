package ca.etsmtl.etsmobile3.data.api

import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile3.data.model.Etudiant
import ca.etsmtl.etsmobile3.data.model.UserCredentials
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
    fun infoEtudiant(@Body body: UserCredentials): LiveData<ApiResponse<Etudiant>>
}