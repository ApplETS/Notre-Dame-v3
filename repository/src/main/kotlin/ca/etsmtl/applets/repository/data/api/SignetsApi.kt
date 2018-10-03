package ca.etsmtl.applets.repository.data.api

import android.arch.lifecycle.LiveData
import ca.etsmtl.applets.repository.data.api.requestbody.signets.EtudiantRequestBody
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeCoursIntervalleSessionsRequestBody
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeDesActivitesEtProfRequestBody
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeDesElementsEvaluationRequestBody
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeDesSeancesRequestBody
import ca.etsmtl.applets.repository.data.api.requestbody.signets.ListeHoraireExamensFinauxRequestBody
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesActivitesEtProf
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeJoursRemplaces
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeProgrammes
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import retrofit2.http.Body
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
    fun listeProgrammes(@Body body: EtudiantRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeProgrammes>>>

    /**
     * Fetches the schedules of the student's final exams with room locations
     *
     * @param body The request body
     * @return The schedules of the student's final exams with room locations
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeHoraireExamensFin")
    fun listeHoraireExamensFinaux(@Body body: ListeHoraireExamensFinauxRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeHoraireExamensFinaux>>>

    /**
     * Fetches a list of courses of the student between two sessions
     *
     * @param body The request body
     * @return A list of courses of the student between the specified sessions
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeCoursIntervalleSessions")
    fun listeCoursIntervalleSessions(@Body body: ListeCoursIntervalleSessionsRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeCours>>>

    /**
     * Fetches a list of the student's activities (courses, labs, etc.) with their schedule and
     * local, as well as the teachers
     *
     * @param body The request body
     * @return A list of the student's activities (courses, labs, etc.) with their schedule and
     * local, as well as the teachers
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeHoraireEtProf")
    fun listeDesActivitesEtProf(@Body body: ListeDesActivitesEtProfRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesActivitesEtProf>>>

    /**
     * Fetches basic information about the student: name, first name, permanent code and balance
     *
     * @param body The request body
     * @return Basic information about the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("infoEtudiant")
    fun infoEtudiant(@Body body: EtudiantRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiEtudiant>>>

    /**
     * Fetches a list of days that replace others
     *
     * @param pSession The session
     * @return A list of days that replace others for the specified session
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("lireJoursRemplaces")
    fun listeJoursRemplaces(@Body pSession: String): LiveData<ApiResponse<ApiSignetsModel<ApiListeJoursRemplaces>>>

    /**
     * Fetches a list of all courses of the student. The is list is sorted by session and acronym.
     *
     * @param body The request body
     * @return A list of all courses of the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeCours")
    fun listeCours(@Body body: EtudiantRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeCours>>>

    /**
     * Fetches a list of the student's evaluations (exams, assignments, etc.)
     *
     * @param body The request body
     * @return A list of the student's evaluations
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeElementsEvaluation")
    fun listeDesElementsEvaluation(@Body body: ListeDesElementsEvaluationRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesElementsEvaluation>>>

    /**
     * Fetches a list of the student's sessions
     *
     * @param body The request body
     * @return A list of sessions of the student
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("listeSessions")
    fun listeSessions(@Body body: EtudiantRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeDeSessions>>>

    /**
     * Fetches the schedule of the sessions for a given course
     *
     * @param body The request body
     * @return A list of the sessions for a given course
     */
    @Headers("Accept: application/json", "Content-Type: application/json", "Accept-Charset: UTF-8")
    @POST("lireHoraireDesSeances")
    fun listeDesSeances(@Body body: ListeDesSeancesRequestBody): LiveData<ApiResponse<ApiSignetsModel<ApiListeDesSeances>>>
}