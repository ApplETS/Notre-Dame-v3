package ca.etsmtl.etsmobile.data.api

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.NonNull
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.HoraireExamenFinal
import ca.etsmtl.etsmobile.data.model.signets.ListeDeCours
import ca.etsmtl.etsmobile.data.model.signets.ListeDeSessions
import ca.etsmtl.etsmobile.data.model.signets.ListeHoraireExamensFinaux
import ca.etsmtl.etsmobile.data.model.signets.ListeProgrammes
import ca.etsmtl.etsmobile.data.model.signets.Session
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

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeProgrammesNoError() {
        enqueueResponse("liste_programmes_no_error.json")

        val apiResponse = api.listeProgrammes(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: SignetsModel<ListeProgrammes> = getValue(apiResponse).body!!
        val errorStr = wrapper.data?.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }

        val listeProgrammes: ListeProgrammes = wrapper.data!!
        val programmes = listeProgrammes.liste!!
        val p0 = programmes[0]!!
        assertEquals("0725", p0.code)
        assertEquals("Microprogramme de 1er cycle en enseignement coopératif I",
                p0.libelle)
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
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeDesProgrammes",
                wrapper.data!!.type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeProgrammesError() {
        enqueueResponse("liste_programmes_error.json")

        val apiResponse = api.listeProgrammes(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: SignetsModel<ListeProgrammes> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeDesProgrammes",
                wrapper.data!!.type
        )
        assertEquals(0, wrapper.data!!.liste!!.size)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeHoraireExamensFinauxNoError() {
        enqueueResponse("liste_horaire_examens_finaux_no_error.json")

        val apiResponse = api.listeHoraireExamensFinaux(
                "AM41234",
                "test!",
                "É2018"
        )
        val wrapper: SignetsModel<ListeHoraireExamensFinaux> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        val listeHoraireExamensFinaux = wrapper.data!!
        assertEquals(4, listeHoraireExamensFinaux.listeHoraire!!.size)
        val horaireExamenFinal0: HoraireExamenFinal = listeHoraireExamensFinaux.listeHoraire!![0]!!
        assertEquals("GPE450", horaireExamenFinal0.sigle)
        assertEquals("01", horaireExamenFinal0.groupe)
        assertEquals("2018-04-13", horaireExamenFinal0.dateExamen)
        assertEquals("13:30", horaireExamenFinal0.heureDebut)
        assertEquals("16:30", horaireExamenFinal0.heureFin)
        assertEquals("A-1302", horaireExamenFinal0.local)
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeHoraireExamensFinaux",
                wrapper.data!!.type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeHoraireExamensFinauxError() {
        enqueueResponse("liste_horaire_examens_finaux_error.json")
        val apiResponse = api.listeHoraireExamensFinaux(
                "AM41234",
                "test!",
                "É2018"
        )
        val wrapper: SignetsModel<ListeHoraireExamensFinaux> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeHoraireExamensFinaux",
                wrapper.data!!.type
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeCoursIntervalleSessionsNoError() {
        enqueueResponse("liste_cours_intervalle_sessions_no_error.json")
        val wrapper: SignetsModel<ListeDeCours> = getValue(api
                .listeCoursIntervalleSessions(
                        "AM41234",
                        "test!",
                        "H2018",
                        "E2018"
                ))
                .body!!
        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        val listeDeCours = wrapper.data!!
        assertEquals(6, listeDeCours.liste!!.size)
        with(listeDeCours.liste!![0]) {
            assertEquals("GPE450", this.sigle)
            assertEquals("01", this.groupe)
            assertEquals("H2018", this.session)
            assertEquals("7365", this.programmeEtudes)
            assertEquals("A+", this.cote)
            assertEquals(3, this.nbCredits)
            assertEquals("Gestion du personnel et relations industrielles", this.titreCours)
        }
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDeCours", listeDeCours.type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeCoursIntervalleSessionsError() {
        enqueueResponse("liste_cours_intervalle_sessions_error.json")
        val apiResponse = api.listeCoursIntervalleSessions(
                "AM41234",
                "test!",
                "H2018t",
                "E2018"
        )
        val wrapper: SignetsModel<ListeDeCours> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals(
                "Session de début invalide:H2018g. L'année doit avoir 4 chiffres.",
                errorStr
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetInfoEtudiantNoError() {
        enqueueResponse("info_etudiant_no_error.json")

        val apiResponse = api
                .infoEtudiant(SignetsUserCredentials("AM41234", "test!"))
        val etudiantWrapper: SignetsModel<Etudiant> = getValue(apiResponse).body!!
        val etudiant = Etudiant(
                "SignetsPourEtudiants.SignetsMobile+Etudiant",
                "Liu                                     ",
                "Philippe            ",
                "LIUP27099105",
                "0,00\$",
                true,
                ""
        )
        assertEquals(etudiant, etudiantWrapper.data!!)
        val errorStr = etudiantWrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetInfoEtudiantError() {
        enqueueResponse("info_etudiant_error.json")

        val apiResponse = api.infoEtudiant(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val etudiantWrapper: SignetsModel<Etudiant> = getValue(apiResponse).body!!

        assertEquals("", etudiantWrapper.data?.nom)
        assertEquals("", etudiantWrapper.data?.prenom)
        assertEquals("", etudiantWrapper.data?.codePerm)
        assertEquals("", etudiantWrapper.data?.soldeTotal)
        val errorStr = etudiantWrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeSessionsNoError() {
        enqueueResponse("liste_sessions_no_error.json")

        val apiResponse = api.listeSessions(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: SignetsModel<ListeDeSessions> = getValue(apiResponse).body!!

        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        assertEquals(6, wrapper.data!!.liste.size)

        val expectedSession = Session(
                "É2018",
                "Été 2018",
                "2018-04-30",
                "2018-08-11",
                "2018-08-01",
                "2018-02-26",
                "2018-03-13",
                "2018-04-30",
                "2018-05-11",
                "2018-05-11",
                "2018-05-22",
                "2018-07-04",
                "1964-01-01"
        )
        assertEquals(expectedSession, wrapper.data!!.liste[5])
        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDeSessions", type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeSessionsError() {
        enqueueResponse("liste_sessions_error.json")

        val apiResponse = api.listeSessions(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: SignetsModel<ListeDeSessions> = getValue(apiResponse).body!!

        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDeSessions", type)
    }
}