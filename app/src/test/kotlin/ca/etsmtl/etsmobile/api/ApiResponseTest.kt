package ca.etsmtl.etsmobile.api

import ca.etsmtl.etsmobile.data.api.ApiResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class ApiResponseTest {
    @Test
    fun exception() {
        val exception = Exception("foo")
        val apiResponse = ApiResponse<String>(exception)
        assertThat(apiResponse.body, nullValue())
        assertThat(apiResponse.code, `is`(500))
        assertThat(apiResponse.errorMessage, `is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse = ApiResponse(Response.success("foo"))
        assertThat(apiResponse.errorMessage, nullValue())
        assertThat(apiResponse.code, `is`(200))
        assertThat(apiResponse.body, `is`("foo"))
    }
}
