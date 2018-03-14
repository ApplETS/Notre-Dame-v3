package ca.etsmtl.etsmobile.repository

import ca.etsmtl.etsmobile.data.api.ApiResponse
import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.data.db.dao.EtudiantDao
import ca.etsmtl.etsmobile.data.model.Etudiant
import ca.etsmtl.etsmobile.data.model.SignetsModel
import ca.etsmtl.etsmobile.data.repository.InfoEtudiantRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull


/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class InfoEtudiantRepositoryTest {
    private lateinit var signetsApi: SignetsApi
    private lateinit var repo: InfoEtudiantRepository
    private lateinit var dao: EtudiantDao

//    @Rule
//    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        signetsApi = mock(SignetsApi::class.java)
        dao = mock(EtudiantDao::class.java)
        repo = InfoEtudiantRepository(signetsApi, dao)
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
        etudiant.data!!.erreur = expectedErrorStr
        val response = Response.success(etudiant)
        val apiResponse = ApiResponse<SignetsModel<Etudiant>>(response)

        assertEquals(expectedErrorStr, repo.getError(apiResponse))
    }
}
