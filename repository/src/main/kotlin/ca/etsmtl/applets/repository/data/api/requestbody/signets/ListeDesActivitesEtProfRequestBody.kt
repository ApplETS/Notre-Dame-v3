package ca.etsmtl.applets.repository.data.api.requestbody.signets

/**
 * Created by Sonphil on 12-08-18.
 */

data class ListeDesActivitesEtProfRequestBody(
    /** The student's universal code **/
    val codeAccesUniversel: String,
    /** The student's password **/
    val motPasse: String,
    /** The session **/
    val pSession: String
)