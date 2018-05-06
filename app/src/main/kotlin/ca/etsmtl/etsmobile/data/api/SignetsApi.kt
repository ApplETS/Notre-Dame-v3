package ca.etsmtl.etsmobile.data.api

import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.ListeDeCours
import ca.etsmtl.etsmobile.data.model.signets.ListeDeSessions
import ca.etsmtl.etsmobile.data.model.signets.ListeDesActivitesEtProf
import ca.etsmtl.etsmobile.data.model.signets.ListeHoraireExamensFinaux
import ca.etsmtl.etsmobile.data.model.signets.ListeJoursRemplaces
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
 * This interface is implemented by Retrofit. It's used to fetch data from Signets web service.
 * https://signets-ens.etsmtl.ca/Secure/WebServices/SignetsMobile.asmx
 *
 * Created by Sonphil on 02-03-18.
 */
@Singleton
interface SignetsApi {
    /**
     * Fetches a list of the student's programs
     *
     * @param body The student's credentials
     * @return A List of the student's programs
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeProgrammes")
    fun listeProgrammes(@Body body: SignetsUserCredentials): LiveData<ApiResponse<SignetsModel<ListeProgrammes>>>

    /**
     * Fetches the schedules of the student's final exams with room locations
     *
     * @param codeAccesUniversel The student's universal code
     * @param motPasse The student's password
     * @param pSession The session
     * @return The schedules of the student's final exams with room locations
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("listeHoraireExamensFin")
    fun listeHoraireExamensFinaux(
        @Field("codeAccesUniversel") codeAccesUniversel: String,
        @Field("motPasse") motPasse: String,
        @Field("pSession") pSession: String
    ): LiveData<ApiResponse<SignetsModel<ListeHoraireExamensFinaux>>>

    /**
     * Fetches a list of courses of the student between two sessions
     *
     * @param codeAccesUniversel The student's universal code
     * @param motPasse The student's password
     * @param sesDebut The interval starting session
     * @param sesFin The interval ending session
     * @return A list of courses of the student between the specified sessions
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("listeHoraireExamensFin")
    fun listeCoursIntervalleSessions(
        @Field("codeAccesUniversel") codeAccesUniversel: String,
        @Field("motPasse") motPasse: String,
        @Field("SesDebut") sesDebut: String,
        @Field("SesFin") sesFin: String
    ): LiveData<ApiResponse<SignetsModel<ListeDeCours>>>

    /**
     * Fetches a list of the student's activities (courses, labs, etc.) with their schedule and
     * local, as well as the teachers
     *
     * @param codeAccesUniversel The student's universal code
     * @param motPasse The student's password
     * @param pSession The session
     * @return A list of the student's activities (courses, labs, etc.) with their schedule and
     * local, as well as the teachers
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("listeHoraireEtProf")
    fun listeDesActivitesEtProf(
        @Field("codeAccesUniversel") codeAccesUniversel: String,
        @Field("motPasse") motPasse: String,
        @Field("pSession") pSession: String
    ): LiveData<ApiResponse<SignetsModel<ListeDesActivitesEtProf>>>

    /**
     * Fetches basic information about the student: name, first name, permanent code and balance
     *
     * @param body The student's credentials
     * @return Basic information about the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("infoEtudiant")
    fun infoEtudiant(@Body body: SignetsUserCredentials): LiveData<ApiResponse<SignetsModel<Etudiant>>>

    /**
     * Fetches a list of days that replace others
     *
     * @param pSession The session
     * @return A list of days that replace others for the specified session
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("lireJoursRemplaces")
    fun listeJoursRemplaces(@Field("pSession") pSession: String): LiveData<ApiResponse<SignetsModel<ListeJoursRemplaces>>>

    /**
     * Fetches a list of all courses of the student. The is list is sorted by session and acronym.
     *
     * @param body The student's credentials
     * @return A list of all courses of the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeCours")
    fun listeCours(@Body body: SignetsUserCredentials): LiveData<ApiResponse<SignetsModel<ListeDeCours>>>

    /**
     * Fetches a list of sessions of the student
     *
     * @param body The student's credentials
     * @return A list of sessions of the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeSessions")
    fun listeSessions(@Body body: SignetsUserCredentials): LiveData<ApiResponse<SignetsModel<ListeDeSessions>>>
}