package ca.etsmtl.repository.data.api.requestbody.signets

/**
 * Created by Sonphil on 12-08-18.
 */

data class ListeDesElementsEvaluationRequestBody(
    /** The student's universal code **/
    val codeAccesUniversel: String,
    /** The student's password **/
    val motPasse: String,
    /** The course number **/
    val pSigle: String,
    /** The group number **/
    val pGroupe: String,
    /** The session **/
    val pSession: String
)