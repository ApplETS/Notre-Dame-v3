package ca.etsmtl.etsmobile.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import ca.etsmtl.etsmobile.InstantAppExecutors
import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.SignetsModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import kotlin.test.assertEquals
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
        val etudiant = SignetsModel<Etudiant>()
        etudiant.data = Etudiant(codePerm = "TEST")
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<SignetsModel<Etudiant>>(response)

        assertNull(repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorApiResponseFail() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<SignetsModel<Etudiant>>(throwable)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorInsideData() {
        val etudiant = SignetsModel<Etudiant>()
        etudiant.data = Etudiant(codePerm = "TEST")
        val expectedErrorStr = "Test error"
        etudiant.data!!.error = expectedErrorStr
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<SignetsModel<Etudiant>>(response)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    class TestSignetsRepository : SignetsRepository(InstantAppExecutors())
}