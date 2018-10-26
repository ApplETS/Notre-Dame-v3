package ca.etsmtl.applets.repository.data.repository

import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.util.networkOrSignetsError
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created by Sonphil on 10-10-18.
 */
@RunWith(JUnit4::class)
class ApiResponseTest {
    @Test
    fun testGetErrorApiResponseNoError() {
        val signetsModel = ApiSignetsModel<TestApiSignetsData>()
        val fakeSignetsData = TestApiSignetsData()
        signetsModel.data = fakeSignetsData
        val response = Response.success(signetsModel)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(response)

        assertNull(apiResponse.networkOrSignetsError)
    }

    @Test
    fun testGetErrorApiResponseFail() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(throwable)

        assertEquals(expectedErrorStr, apiResponse.errorMessage)
    }

    @Test
    fun testGetErrorInsideData() {
        val signetsModel = ApiSignetsModel<TestApiSignetsData>()
        val expectedErrorStr = "Foo error"
        val testSignetsData = TestApiSignetsData(expectedErrorStr)
        signetsModel.data = testSignetsData
        val response = Response.success(signetsModel)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(response)

        assertEquals(expectedErrorStr, apiResponse.networkOrSignetsError)
    }

    data class TestApiSignetsData(override var erreur: String? = null) : ApiSignetsData()
}