package ca.etsmtl.etsmobile.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import ca.etsmtl.etsmobile.InstantAppExecutors
import ca.etsmtl.etsmobile.LiveDataTestUtil.getValue
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.model.signets.SignetsData
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.repository.signets.SignetsRepository
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
class SignetsRepositoryTest {
    private lateinit var repo: SignetsRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repo = TestSignetsRepository()
    }

    @Test
    fun testGetErrorApiResponseNoError() {
        val signetsModel = SignetsModel<TestSignetsData>()
        val fakeSignetsData = TestSignetsData()
        signetsModel.data = fakeSignetsData
        val response = Response.success(signetsModel)
        val apiResponse = ApiResponse<SignetsModel<TestSignetsData>>(response)

        assertNull(repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorApiResponseFail() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<SignetsModel<TestSignetsData>>(throwable)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorInsideData() {
        val signetsModel = SignetsModel<TestSignetsData>()
        val expectedErrorStr = "Foo error"
        val testSignetsData = TestSignetsData(expectedErrorStr)
        signetsModel.data = testSignetsData
        val response = Response.success(signetsModel)
        val apiResponse = ApiResponse<SignetsModel<TestSignetsData>>(response)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testTransformApiLiveDataWithoutError() {
        val signetsModel = SignetsModel<TestSignetsData>()
        val fakeSignetsData = TestSignetsData()
        signetsModel.data = fakeSignetsData
        val response = Response.success(signetsModel)
        val expectedApiResponse = ApiResponse<SignetsModel<TestSignetsData>>(response)
        val liveData = MutableLiveData<ApiResponse<SignetsModel<TestSignetsData>>>()
        liveData.value = expectedApiResponse
        val actualApiResponse = getValue(repo.transformApiLiveData(liveData))

        assertEquals(expectedApiResponse, actualApiResponse)
    }

    @Test
    fun testTransformApiLiveDataWithError() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<SignetsModel<TestSignetsData>>(throwable)
        val liveData = MutableLiveData<ApiResponse<SignetsModel<TestSignetsData>>>()
        liveData.value = apiResponse
        val resultApiResponse = getValue(repo.transformApiLiveData(liveData))

        assertNotEquals(apiResponse, resultApiResponse)
        assertFalse(apiResponse.isSuccessful)
        assertEquals(expectedErrorStr, apiResponse.errorMessage)
    }

    class TestSignetsRepository : SignetsRepository(InstantAppExecutors())

    data class TestSignetsData(var erreur: String? = null) : SignetsData() {
        override fun getError(): String? {
            return erreur
        }
    }
}