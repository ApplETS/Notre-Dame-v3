package ca.etsmtl.etsmobile.data.api

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.NonNull
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.ListeProgrammes
import ca.etsmtl.etsmobile.data.model.signets.SignetsData
import ca.etsmtl.etsmobile.data.model.signets.SignetsModel
import ca.etsmtl.etsmobile.data.model.signets.SignetsUserCredentials
import ca.etsmtl.etsmobile.util.ApplicationJsonAdapterFactory
import ca.etsmtl.etsmobile.util.LiveDataCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.Collections
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class SignetsApiTest {
    companion object {
        // Time out in seconds
        val TIME_OUT = 2
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SignetsApi
    private val moshi = Moshi.Builder().add(ApplicationJsonAdapterFactory.INSTANCE).build()

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(SignetsApi::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Throws(InterruptedException::class)
    private inline fun <reified T : SignetsData> getValue(@NonNull liveData: LiveData<ApiResponse<SignetsModel<T>>>): ApiResponse<SignetsModel<T>> {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<ApiResponse<SignetsModel<T>>> {
            override fun onChanged(response: ApiResponse<SignetsModel<T>>?) {
                data[0] = response
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(TIME_OUT.toLong(), TimeUnit.SECONDS)
        //noinspection unchecked
        return data[0] as ApiResponse<SignetsModel<T>>
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getInfoEtudiantNoError() {
        enqueueResponse("info_etudiant_no_error.json")

        val etudiantWrapper: SignetsModel<Etudiant> = getValue(api.infoEtudiant(SignetsUserCredentials("AM41234", "test!"))).body!!

        assertEquals("Liu", etudiantWrapper.data?.nom)
        assertEquals("Philippe", etudiantWrapper.data?.prenom)
        assertEquals("LIUP27099105", etudiantWrapper.data?.codePerm)
        assertEquals("0,00\$", etudiantWrapper.data?.soldeTotal)
        assertTrue { etudiantWrapper.data?.masculin!! }
        val errorStr = etudiantWrapper.data?.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getInfoEtudiantError() {
        enqueueResponse("info_etudiant_error.json")

        val etudiantWrapper: SignetsModel<Etudiant> = getValue(api.infoEtudiant(SignetsUserCredentials("AM41234", "test!"))).body!!

        assertEquals("", etudiantWrapper.data?.nom)
        assertEquals("", etudiantWrapper.data?.prenom)
        assertEquals("", etudiantWrapper.data?.codePerm)
        assertEquals("", etudiantWrapper.data?.soldeTotal)
        val errorStr = etudiantWrapper.data?.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getListeProgrammesNoError() {
        enqueueResponse("liste_programmes_no_error.json")

        val wrapper: SignetsModel<ListeProgrammes> = getValue(api.listeProgrammes(SignetsUserCredentials("AM41234", "test!"))).body!!
        val errorStr = wrapper.data?.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }

        val listeProgrammes: ListeProgrammes = wrapper.data!!
        val programmes = listeProgrammes.liste!!
        val p0 = programmes[0]!!
        assertEquals("0725", p0.code)
        assertEquals("Microprogramme de 1er cycle en enseignement coopératif I", p0.libelle)
        assertEquals("", p0.profil)
        assertEquals("diplômé", p0.statut)
        assertEquals("É2016", p0.sessionDebut)
        assertEquals("É2016", p0.sessionFin)
        assertEquals("", p0.moyenne)
        assertEquals(0, p0.nbEquivalences)
        assertEquals(1, p0.nbCrsReussis)
        assertEquals(0, p0.nbCrsEchoues)
        assertEquals(0, p0.nbCreditsInscrits)
        assertEquals(9, p0.nbCreditsCompletes)
        assertEquals(9, p0.nbCreditsPotentiels)
        assertEquals(0, p0.nbCreditsRecherche)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getListeProgrammesError() {
        enqueueResponse("liste_programmes_error.json")

        val wrapper: SignetsModel<ListeProgrammes> = getValue(api.listeProgrammes(SignetsUserCredentials("AM41234", "test!"))).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeDesProgrammes", wrapper.data!!.type)
        assertEquals(0, wrapper.data!!.liste!!.size)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, Collections.emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)))
    }
}