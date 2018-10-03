package ca.etsmtl.applets.repository.data.api.requestbody.signets

/**
 * Created by Sonphil on 12-08-18.
 */

data class ListeCoursIntervalleSessionsRequestBody(
    /** The student's universal code **/
    val codeAccesUniversel: String,
    /** The student's password **/
    val motPasse: String,
    /** The interval starting session **/
    val sesDebut: String,
    /** The interval ending session **/
    val sesFin: String
)