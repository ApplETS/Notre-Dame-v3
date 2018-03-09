package ca.etsmtl.etsmobile3.repository

import android.text.TextUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ca.etsmtl.etsmobile3.data.api.ApiResponse
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.Etudiant
import ca.etsmtl.etsmobile3.data.model.EtudiantData
import ca.etsmtl.etsmobile3.data.repository.InfoEtudiantRepository
import org.junit.Rule
import org.junit.Before
import org.mockito.Mockito.mock
import retrofit2.Response
import kotlin.test.*


/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class InfoEtudiantRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: InfoEtudiantRepository

//    @Rule
//    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsApi = mock(SignetsApi::class.java)
        repo = InfoEtudiantRepository(signetsApi)
    }

    @Test
    fun testGetErrorApiResponseNoError() {
        val etudiant = Etudiant()
        etudiant.data = EtudiantData()
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<Etudiant>(response)

        assertNull(repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorApiResponseFail() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<Etudiant>(throwable)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorInsideData() {
        val etudiant = Etudiant()
        etudiant.data = EtudiantData()
        val expectedErrorStr = "Test error"
        etudiant.data!!.erreur = expectedErrorStr
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<Etudiant>(response)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }
}
