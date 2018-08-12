package ca.etsmtl.repository.data.api

import android.arch.lifecycle.LiveData
import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesActivitesEtProf
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
import ca.etsmtl.repository.data.api.response.signets.ApiListeJoursRemplaces
import ca.etsmtl.repository.data.api.response.signets.ApiListeProgrammes
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.repository.data.model.SignetsUserCredentials
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
    fun listeProgrammes(@Body body: SignetsUserCredentials): LiveData<ApiResponse<ApiSignetsModel<ApiListeProgrammes>>>

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
    ): LiveData<ApiResponse<ApiSignetsModel<ApiListeHoraireExamensFinaux>>>

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
    @POST("listeCoursIntervalleSessions")
    fun listeCoursIntervalleSessions(
        @Field("codeAccesUniversel") codeAccesUniversel: String,
        @Field("motPasse") motPasse: String,
        @Field("SesDebut") sesDebut: String,
        @Field("SesFin") sesFin: String
    ): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeCours>>>

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
    ): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesActivitesEtProf>>>

    /**
     * Fetches basic information about the student: name, first name, permanent code and balance
     *
     * @param body The student's credentials
     * @return Basic information about the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("infoEtudiant")
    fun infoEtudiant(@Body body: SignetsUserCredentials): LiveData<ApiResponse<ApiSignetsModel<ApiEtudiant>>>

    /**
     * Fetches a list of days that replace others
     *
     * @param pSession The session
     * @return A list of days that replace others for the specified session
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("lireJoursRemplaces")
    fun listeJoursRemplaces(@Field("pSession") pSession: String): LiveData<ApiResponse<ApiSignetsModel<ApiListeJoursRemplaces>>>

    /**
     * Fetches a list of all courses of the student. The is list is sorted by session and acronym.
     *
     * @param body The student's credentials
     * @return A list of all courses of the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeCours")
    fun listeCours(@Body body: SignetsUserCredentials): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeCours>>>

    /**
     * Fetches a list of the student's evaluations (exams, assignments, etc.)
     *
     * @param codeAccesUniversel The student's universal code
     * @param motPasse The student's password
     * @param pSigle The course number
     * @param pGroupe The group number
     * @param pSession The session
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("listeElementsEvaluation")
    fun listeDesElementsEvaluation(
        @Field("codeAccesUniversel") codeAccesUniversel: String,
        @Field("motPasse") motPasse: String,
        @Field("pSigle") pSigle: String,
        @Field("pGroupe") pGroupe: String,
        @Field("pSession") pSession: String
    ): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesElementsEvaluation>>>

    /**
     * Fetches a list of the student's sessions
     *
     * @param body The student's credentials
     * @return A list of sessions of the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeSessions")
    fun listeSessions(@Body body: SignetsUserCredentials): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeSessions>>>

    /**
     * Fetches the schedule of the sessions for a given course
     *
     * @param codeAccesUniversel The student's universal code
     * @param motPasse The student's password
     * @param pCoursGroupe The group number
     * @param pSession The session
     * @param pDateDebut The starting date
     * @param pDateFin The end date
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @FormUrlEncoded
    @POST("lireHoraireDesSeances")
    fun listeDesSeances(
        @Field("codeAccesUniversel") codeAccesUniversel: String,
        @Field("motPasse") motPasse: String,
        @Field("pCoursGroupe") pSigle: String,
        @Field("pSession") pSession: String,
        @Field("pDateDebut") pDateDebut: String,
        @Field("pDateFin") pDateFin: String
    ): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesSeances>>>
}