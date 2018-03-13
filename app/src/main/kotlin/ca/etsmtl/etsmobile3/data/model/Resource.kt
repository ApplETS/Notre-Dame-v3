package ca.etsmtl.etsmobile3.data.model

/**
 * Created by Sonphil on 31-08-17.
 */

class Resource<T> private constructor(val status: Int, val data: T?, val message: String?) {
    companion object {
        const val SUCCESS = 200
        const val LOADING = 350
        const val ERROR = 500

        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
