package data.api.response.signets

import kotlinx.serialization.Serializable

/**
 * Created by Sonphil on 09-03-18.
 */

@Serializable
data class ApiSignetsModel<T : ApiSignetsData>(
    var data: T? = null
)
