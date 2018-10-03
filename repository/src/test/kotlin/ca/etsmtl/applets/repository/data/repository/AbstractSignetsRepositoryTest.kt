package ca.etsmtl.applets.repository.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import ca.etsmtl.applets.repository.LiveDataTestUtil.getValue
import ca.etsmtl.applets.repository.data.api.ApiResponse
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.applets.repository.data.repository.signets.SignetsRepository
import ca.etsmtl.applets.repository.util.InstantAppExecutors
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

/**
 * Created by Sonphil on 17-03-18.
 */
@RunWith(JUnit4::class)
class AbstractSignetsRepositoryTest {
    private lateinit var repo: SignetsRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repo = TestSignetsRepository()
    }

    @Test
    fun testGetErrorApiResponseNoError() {
        val signetsModel = ApiSignetsModel<TestApiSignetsData>()
        val fakeSignetsData = TestApiSignetsData()
        signetsModel.data = fakeSignetsData
        val response = Response.success(signetsModel)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(response)

        assertNull(repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorApiResponseFail() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(throwable)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorInsideData() {
        val signetsModel = ApiSignetsModel<TestApiSignetsData>()
        val expectedErrorStr = "Foo error"
        val testSignetsData = TestApiSignetsData(expectedErrorStr)
        signetsModel.data = testSignetsData
        val response = Response.success(signetsModel)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(response)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testTransformApiLiveDataWithoutError() {
        val signetsModel = ApiSignetsModel<TestApiSignetsData>()
        val fakeSignetsData = TestApiSignetsData()
        signetsModel.data = fakeSignetsData
        val response = Response.success(signetsModel)
        val expectedApiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(response)
        val liveData = MutableLiveData<ApiResponse<ApiSignetsModel<TestApiSignetsData>>>()
        liveData.value = expectedApiResponse
        val actualApiResponse = getValue(repo.transformApiLiveData(liveData))

        assertEquals(expectedApiResponse, actualApiResponse)
    }

    @Test
    fun testTransformApiLiveDataWithError() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<ApiSignetsModel<TestApiSignetsData>>(throwable)
        val liveData = MutableLiveData<ApiResponse<ApiSignetsModel<TestApiSignetsData>>>()
        liveData.value = apiResponse
        val resultApiResponse = getValue(repo.transformApiLiveData(liveData))

        assertNotEquals(apiResponse, resultApiResponse)
        assertFalse(apiResponse.isSuccessful)
        assertEquals(expectedErrorStr, apiResponse.errorMessage)
    }

    class TestSignetsRepository : SignetsRepository(InstantAppExecutors())

    data class TestApiSignetsData(var erreur: String? = null) : ApiSignetsData() {
        override fun getError(): String? {
            return erreur
        }
    }
}