package ca.etsmtl.applets.repository.data.api.response.signets

/**
 * Contains the error field returned by Signets
 *
 * The webservice provides several functions, which return data in JSON format. The first
 * information returned is an error value. If this field is empty, it means that the request has
 * worked, and that the rest of the answer contains the requested data. If the field is not empty,
 * it contains the description of the error encountered: universal code or password invalid,
 * wrong parameter format, service refused because the student has failed to pay his student fees,
 * etc.
 *
 * Created by Sonphil on 17-03-18.
 */
abstract class ApiSignetsData {
    abstract val erreur: String?
}