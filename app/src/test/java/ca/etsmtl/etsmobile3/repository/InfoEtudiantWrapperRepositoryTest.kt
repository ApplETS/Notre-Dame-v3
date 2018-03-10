package ca.etsmtl.etsmobile3.repository

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ca.etsmtl.etsmobile3.data.api.ApiResponse
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.EtudiantWrapper
import ca.etsmtl.etsmobile3.data.model.Etudiant
import ca.etsmtl.etsmobile3.data.repository.InfoEtudiantRepository
import org.junit.Before
import org.mockito.Mockito.mock
import retrofit2.Response
import kotlin.test.*


/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class InfoEtudiantWrapperRepositoryTest {
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
        val etudiant = EtudiantWrapper()
        etudiant.data = Etudiant()
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<EtudiantWrapper>(response)

        assertNull(repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorApiResponseFail() {
        val expectedErrorStr = "Test error"
        val throwable = Throwable(expectedErrorStr)
        val apiResponse = ApiResponse<EtudiantWrapper>(throwable)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }

    @Test
    fun testGetErrorInsideData() {
        val etudiant = EtudiantWrapper()
        etudiant.data = Etudiant()
        val expectedErrorStr = "Test error"
        etudiant.data!!.erreur = expectedErrorStr
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<EtudiantWrapper>(response)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }
}
