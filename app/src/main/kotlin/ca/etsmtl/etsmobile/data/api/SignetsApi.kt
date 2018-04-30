package ca.etsmtl.etsmobile.data.api

import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.ListeHoraireExamensFinaux
import ca.etsmtl.etsmobile.data.model.signets.ListeProgrammes
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

/**
 * Created by Sonphil on 02-03-18.
 */
@Singleton
interface SignetsApi {
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeProgrammes")
    fun listeProgrammes(@Body body: SignetsUserCredentials): LiveData<ApiResponse<SignetsModel<ListeProgrammes>>>

    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("listeHoraireExamensFin")
    fun listeHoraireExamensFinaux(@Field("codeAccesUniversel") codeAccesUniversel: String,
                                  @Field("motPasse") motPasse: String,
                                  @Field("pSession") pSession: String): LiveData<ApiResponse<SignetsModel<ListeHoraireExamensFinaux>>>

    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("infoEtudiant")
    fun infoEtudiant(@Body body: SignetsUserCredentials): LiveData<ApiResponse<SignetsModel<Etudiant>>>
}